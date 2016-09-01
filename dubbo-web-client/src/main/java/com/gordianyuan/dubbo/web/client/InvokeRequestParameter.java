package com.gordianyuan.dubbo.web.client;

import com.google.common.base.MoreObjects;

public class InvokeRequestParameter {

  private String type;

  private Object value;

  protected InvokeRequestParameter() {
  }

  public InvokeRequestParameter(String type, Object value) {
    setType(type);
    setValue(value);
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = adjustType(type);
  }

  private String adjustType(String type) {
    if ("$null".equalsIgnoreCase(type)) {
      return null;
    }
    if ("string".equalsIgnoreCase(type)) {
      return "java.lang.String";
    }
    return type;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = adjustValue(value);
  }

  private Object adjustValue(Object value) {
    if (value instanceof String && "$null".equalsIgnoreCase((String) value)) {
      return null;
    }
    return value;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("type", type)
        .add("value", value)
        .toString();
  }

}
