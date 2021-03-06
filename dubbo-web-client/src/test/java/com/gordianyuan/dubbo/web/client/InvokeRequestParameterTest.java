package com.gordianyuan.dubbo.web.client;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.exparity.hamcrest.date.DateMatchers.within;
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
    assertThat((String) parameter.getValue(), equalTo("Lisa"));
  }

  @Test
  public void createStringParameter() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("string", "Lisa");
    assertThat(parameter.getType(), equalTo(String.class.getName()));
    assertThat((String) parameter.getValue(), equalTo("Lisa"));
  }

  @Test
  public void createListParameterJsonArrayFormat() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("list", "[\"Alex\", \"Bob\"]");
    assertThat(parameter.getType(), equalTo("java.util.List"));
    assertThat(parameter.getValue(), instanceOf(List.class));
    List value = (List) parameter.getValue();
    assertThat(value.size(), equalTo(2));
    assertThat((String) value.get(0), equalTo("Alex"));
    assertThat((String) value.get(1), equalTo("Bob"));
  }

  @Test
  public void createListParameterCommaSeparated() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("list", "Alex,Bob");
    assertThat(parameter.getType(), equalTo("java.util.List"));
    assertThat(parameter.getValue(), instanceOf(List.class));
    List value = (List) parameter.getValue();
    assertThat(value.size(), equalTo(2));
    assertThat((String) value.get(0), equalTo("Alex"));
    assertThat((String) value.get(1), equalTo("Bob"));
  }

  @Test
  public void createListParameterDerivedClass() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("java.util.ArrayList", "Alex,Bob");
    assertThat(parameter.getType(), equalTo("java.util.ArrayList"));
    assertThat(parameter.getValue(), instanceOf(List.class));
    List value = (List) parameter.getValue();
    assertThat(value.size(), equalTo(2));
    assertThat((String) value.get(0), equalTo("Alex"));
    assertThat((String) value.get(1), equalTo("Bob"));
  }

  @Test
  public void createMapParameterJsonArrayFormat() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("map", "{\"a\":\"1\"}");
    assertThat(parameter.getType(), equalTo("java.util.Map"));
    assertThat(parameter.getValue(), instanceOf(Map.class));
    Map value = (Map) parameter.getValue();
    assertThat(value.size(), equalTo(1));
    assertThat((String) value.get("a"), equalTo("1"));
  }

  @Test
  public void createMapParameterDerivedClass() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("java.util.HashMap", "{\"a\":\"1\"}");
    assertThat(parameter.getType(), equalTo("java.util.HashMap"));
    assertThat(parameter.getValue(), instanceOf(Map.class));
    Map value = (Map) parameter.getValue();
    assertThat(value.size(), equalTo(1));
    assertThat((String) value.get("a"), equalTo("1"));
  }

  @Test
  public void createNullValueParameter() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("string", "$null");
    assertThat(parameter.getValue(), is(nullValue()));
  }

  @Test
  public void createPojoParameter() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("User", "{\"username\":\"alex\"}");
    assertThat(parameter.getType(), equalTo("User"));
    assertThat(parameter.getValue(), instanceOf(Map.class));
    Map value = (Map) parameter.getValue();
    assertThat(value.size(), equalTo(1));
    assertThat((String) value.get("username"), equalTo("alex"));
  }

  @Test
  public void createDateParameter() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("java.util.Date", "2016-07-18");
    assertThat(parameter.getType(), equalTo("java.util.Date"));
    assertThat(parameter.getValue(), instanceOf(Date.class));

    Calendar calendar = Calendar.getInstance();
    calendar.set(2016, 6, 18, 0, 0, 0);
    assertThat((Date) parameter.getValue(), within(1, TimeUnit.SECONDS, calendar.getTime()));
  }

  @Test
  public void createDateParameterWithTime() {
    InvokeRequestParameter parameter = new InvokeRequestParameter("java.util.Date", "2016-07-18 18:06:29");
    assertThat(parameter.getType(), equalTo("java.util.Date"));
    assertThat(parameter.getValue(), instanceOf(Date.class));

    Calendar calendar = Calendar.getInstance();
    calendar.set(2016, 6, 18, 18, 6, 29);
    assertThat((Date) parameter.getValue(), within(1, TimeUnit.SECONDS, calendar.getTime()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void createDateParameterInvalidFormat() {
    new InvokeRequestParameter("java.util.Date", "2016/07/18");
  }

}