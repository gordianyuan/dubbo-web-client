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
  public void createSpecifiedSParameter() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("java.lang.String", "Lisa");
    assertThat(parameter.getType(), equalTo(String.class.getName()));
    assertThat(parameter.getValue(), equalTo("Lisa"));
  }

  @Test
  public void createStringParameter() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("string", "Lisa");
    assertThat(parameter.getType(), equalTo(String.class.getName()));
    assertThat(parameter.getValue(), equalTo("Lisa"));
  }

  @Test
  public void createListParameterJsonArrayFormat() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("list", "[\"Alex\", \"Bob\"]");
    assertThat(parameter.getType(), equalTo("java.util.List"));
    assertThat(parameter.getValue(), instanceOf(List.class));
    List value = (List) parameter.getValue();
    assertThat(value.size(), equalTo(2));
    assertThat(value.get(0), equalTo("Alex"));
    assertThat(value.get(1), equalTo("Bob"));
  }

  @Test
  public void createListParameterCommaSeparated() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("list", "Alex,Bob");
    assertThat(parameter.getType(), equalTo("java.util.List"));
    assertThat(parameter.getValue(), instanceOf(List.class));
    List value = (List) parameter.getValue();
    assertThat(value.size(), equalTo(2));
    assertThat(value.get(0), equalTo("Alex"));
    assertThat(value.get(1), equalTo("Bob"));
  }

  @Test
  public void createMapParameterJsonArrayFormat() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("map", "{\"a\":\"1\"}");
    assertThat(parameter.getType(), equalTo("java.util.Map"));
    assertThat(parameter.getValue(), instanceOf(Map.class));
    Map value = (Map) parameter.getValue();
    assertThat(value.size(), equalTo(1));
    assertThat(value.get("a"), equalTo("1"));
  }

  @Test
  public void createNullValueParameter() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("string", "$null");
    assertThat(parameter.getValue(), is(nullValue()));
  }

}