package com.gordianyuan.dubbo.web.client;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.util.List;

public class InvokeRequest {

  private String registryAddress;

  private String referenceInterface;

  private String methodName;

  private List<InvokeRequestParameter> params = Lists.newArrayList();

  public String getRegistryAddress() {
    return registryAddress;
  }

  public void setRegistryAddress(String registryAddress) {
    this.registryAddress = registryAddress;
  }

  public String getReferenceInterface() {
    return referenceInterface;
  }

  public void setReferenceInterface(String referenceInterface) {
    this.referenceInterface = referenceInterface;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public List<InvokeRequestParameter> getParams() {
    return params;
  }

  public void setParams(List<InvokeRequestParameter> params) {
    this.params = params;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("registryAddress", registryAddress)
        .add("referenceInterface", referenceInterface)
        .add("methodName", methodName)
        .add("params", params)
        .toString();
  }

  public String[] getParameterTypes() {
    List<String> types = Lists.newArrayList();
    for (InvokeRequestParameter param : getParams()) {
      types.add(param.getType());
    }
    return types.toArray(new String[0]);
  }

  public Object[] getParameterValues() {
    List<Object> values = Lists.newArrayList();
    for (InvokeRequestParameter param : getParams()) {
      values.add(param.getValue());
    }
    return values.toArray(new Object[0]);
  }

}
