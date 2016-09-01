package com.gordianyuan.dubbo.web.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DubboWebClientController {

  private static Logger log = LoggerFactory.getLogger(DubboWebClientController.class);

  @Autowired
  private DubboService dubboService;

  @RequestMapping(value = "/invoke", method = RequestMethod.POST)
  public Object invoke(@RequestBody InvokeRequest request) {
    log.debug("request: {}", request);

    Object result = dubboService.invoke(request);
    log.debug("result: {}", result);
    return result;
  }

}
