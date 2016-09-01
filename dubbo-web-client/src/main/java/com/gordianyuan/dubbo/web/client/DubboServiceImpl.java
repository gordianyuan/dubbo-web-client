package com.gordianyuan.dubbo.web.client;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class DubboServiceImpl implements DubboService {

  private static final String REFERENCE_CACHE_KEY_SEPARATOR = "|";

  private static final Splitter REFERENCE_CACHE_KEY_SPLITTER = Splitter
      .on(REFERENCE_CACHE_KEY_SEPARATOR).omitEmptyStrings().trimResults();

  private LoadingCache<String, RegistryConfig> registryCache;

  private LoadingCache<String, ReferenceConfig<GenericService>> referenceCache;

  @Autowired
  public DubboServiceImpl(ApplicationConfig application) {
    registryCache = CacheBuilder.newBuilder()
        .maximumSize(1024)
        .expireAfterWrite(12, TimeUnit.HOURS)
        .build(new CacheLoader<String, RegistryConfig>() {
          @Override
          public RegistryConfig load(String registryAddress) throws Exception {
            return new RegistryConfig(registryAddress);
          }
        });

    referenceCache = CacheBuilder.newBuilder()
        .maximumSize(32768)
        .expireAfterWrite(12, TimeUnit.HOURS)
        .build(new CacheLoader<String, ReferenceConfig<GenericService>>() {
          @Override
          public ReferenceConfig<GenericService> load(String key) throws Exception {
            List<String> values = REFERENCE_CACHE_KEY_SPLITTER.splitToList(key);
            Preconditions.checkArgument(values.size() == 2);

            String registryAddress = values.get(0);
            String referenceInterface = values.get(1);

            RegistryConfig registry = registryCache.get(registryAddress);

            ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
            reference.setApplication(application);
            reference.setRegistry(registry);
            reference.setGeneric(true);
            reference.setInterface(referenceInterface);
            return reference;
          }
        });
  }

  @Override
  public Object invoke(InvokeRequest request) {
    GenericService genericService = getGenericService(
        request.getRegistryAddress(), request.getReferenceInterface());

    String method = request.getMethodName();
    String[] parameterTypes = request.getParameterTypes();
    Object[] parameterValues = request.getParameterValues();
    return genericService.$invoke(method, parameterTypes, parameterValues);
  }

  private GenericService getGenericService(String registryAddress, String referenceInterface) {
    Preconditions.checkNotNull(registryAddress);
    Preconditions.checkNotNull(referenceInterface);

    ReferenceConfig<GenericService> reference = null;
    try {
      String cacheKey = registryAddress + "|" + referenceInterface;
      reference = referenceCache.get(cacheKey);
    } catch (ExecutionException e) {
      Throwables.propagate(e);
    }
    return reference.get();
  }

}
