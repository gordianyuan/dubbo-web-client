package com.gordianyuan.dubbo.web.client;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class InvokeRequestParameterTest {

  @Test
  public void setType() {
    InvokeRequestParameter parameter = new InvokeRequestParameter();

    parameter.setType("java.lang.String");
    assertThat(parameter.getType(), equalTo("java.lang.String"));

    parameter.setType("string");
    assertThat(parameter.getType(), equalTo(String.class.getName()));
  }

  @Test
  public void setValue() {
    InvokeRequestParameter parameter = new InvokeRequestParameter();

    parameter.setValue("$null");
    assertThat(parameter.getValue(), is(nullValue()));
  }

}