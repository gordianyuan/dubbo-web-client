package com.gordianyuan.dubbo.web.client;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
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

    parameter.setType("list");
    assertThat(parameter.getType(), equalTo(List.class.getName()));

    parameter.setType("map");
    assertThat(parameter.getType(), equalTo(Map.class.getName()));
  }

  @Test
  public void setValue() {
    InvokeRequestParameter parameter = new InvokeRequestParameter();

    parameter.setValue("$null");
    assertThat(parameter.getValue(), is(nullValue()));

    parameter.setValue("{a: 1}");
    assertThat(parameter.getValue(), instanceOf(Map.class));

    parameter.setValue("['Alex', 'Bob']");
    assertThat(parameter.getValue(), instanceOf(List.class));
  }

}