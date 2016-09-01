package com.gordianyuan.dubbo.web.client;

import com.google.common.base.MoreObjects;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class InvokeRequestParameter {

  private static final Logger log = LoggerFactory.getLogger(InvokeRequestParameter.class);

  private String type;

  private Object value;

  private static final ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
  }

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
    if ("list".equalsIgnoreCase(type)) {
      return "java.util.List";
    }
    if ("map".equalsIgnoreCase(type)) {
      return "java.util.Map";
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
    if (value == null) {
      return null;
    }

    if (value instanceof String) {
      String stringValue = ((String) value).trim();

      if (stringValue.equalsIgnoreCase("$null")) {
        return null;
      }

      if (stringValue.startsWith("{")) {
        try {
          return parseJsonStringToMap(stringValue);
        } catch (IOException e) {
          log.error("It is not a valid JSON string. {}", e.getMessage());
        }
      }

      if (stringValue.startsWith("[")) {
        try {
          return parseJsonStringToList(stringValue);
        } catch (IOException e) {
          log.error("It is not a valid JSON string. {}", e.getMessage());
        }
      }

      return stringValue;
    }

    return value;
  }

  private Map<String, Object> parseJsonStringToMap(String jsonString) throws IOException {
    return mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
    });
  }

  private List<Object> parseJsonStringToList(String jsonString) throws IOException {
    return mapper.readValue(jsonString, new TypeReference<List<Object>>() {
    });
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("type", type)
        .add("value", value)
        .toString();
  }

}
