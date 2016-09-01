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

    parameter.setType("int");
    assertThat(parameter.getType(), equalTo(Integer.class.getName()));

    parameter.setType("integer");
    assertThat(parameter.getType(), equalTo(Integer.class.getName()));

    parameter.setType("char");
    assertThat(parameter.getType(), equalTo(Character.class.getName()));

    parameter.setType("character");
    assertThat(parameter.getType(), equalTo(Character.class.getName()));

    parameter.setType("float");
    assertThat(parameter.getType(), equalTo(Float.class.getName()));

    parameter.setType("short");
    assertThat(parameter.getType(), equalTo(Short.class.getName()));

    parameter.setType("long");
    assertThat(parameter.getType(), equalTo(Long.class.getName()));
  }

  @Test
  public void setValue() {
    InvokeRequestParameter parameter = new InvokeRequestParameter();

    parameter.setValue("$null");
    assertThat(parameter.getValue(), is(nullValue()));
  }

}