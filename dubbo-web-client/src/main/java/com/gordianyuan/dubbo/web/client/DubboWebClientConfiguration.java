package com.gordianyuan.dubbo.web.client;

import com.alibaba.dubbo.config.ApplicationConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboWebClientConfiguration {

  @Bean
  public ApplicationConfig applicationConfig(@Value("${app.name}") String appName) {
    return new ApplicationConfig(appName);
  }

}
