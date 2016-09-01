package com.gordianyuan.dubbo.web.client;

import com.google.common.collect.ImmutableList;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class InvokeRequestTest {

  @Test
  public void getParameterTypes() {
    InvokeRequest request = new InvokeRequest();
    assertThat(request.getParameterTypes().length, equalTo(0));

    request.setParams(Collections.singletonList(new InvokeRequestParameter("java.lang.String", "Gordian")));
    assertThat(request.getParameterTypes().length, equalTo(1));
    assertThat(request.getParameterTypes()[0], equalTo("java.lang.String"));

    request.setParams(Arrays.asList(new InvokeRequestParameter("java.lang.String", "Gordian"),
        new InvokeRequestParameter("java.lang.Integer", 2)));
    assertThat(request.getParameterTypes().length, equalTo(2));
    assertThat(request.getParameterTypes()[0], equalTo("java.lang.String"));
    assertThat(request.getParameterTypes()[1], equalTo("java.lang.Integer"));
  }

  @Test
  public void getParameterValues() {
    InvokeRequest request = new InvokeRequest();
    assertThat(request.getParameterValues().length, equalTo(0));

    request.setParams(ImmutableList.of(new InvokeRequestParameter("java.lang.String", "Gordian")));
    assertThat(request.getParameterValues().length, equalTo(1));
    assertThat(request.getParameterValues()[0], equalTo("Gordian"));

    request.setParams(ImmutableList.of(new InvokeRequestParameter("java.lang.String", "Gordian"),
        new InvokeRequestParameter("java.lang.Integer", 1)));
    assertThat(request.getParameterValues().length, equalTo(2));
    assertThat(request.getParameterValues()[0], equalTo("Gordian"));
    assertThat(request.getParameterValues()[1], equalTo(1));

    // test $null value
    request.setParams(ImmutableList.of(new InvokeRequestParameter("string", "$null")));
    assertThat(request.getParameterValues().length, equalTo(1));
    assertThat(request.getParameterValues()[0], is(nullValue()));
  }

}