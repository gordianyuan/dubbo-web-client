package com.gordianyuan.dubbo.web.client;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

  private static final String NULL_STRING = "$null";

  private String type;

  private Object value;

  private static final ObjectMapper mapper = new ObjectMapper();

  private static final Splitter SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();

  static {
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
  }

  @JsonCreator
  public InvokeRequestParameter(
      @JsonProperty("type") String type,
      @JsonProperty("value") Object value) {
    Preconditions.checkNotNull(type);

    this.type = adjustType(type);
    this.value = adjustValue(value, this.type);
  }

  public String getType() {
    return type;
  }

  private String adjustType(String type) {
    type = type.trim();

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

  private Object adjustValue(Object value, String type) {

    if (value == null || !(value instanceof String)) {
      return value;
    }

    String stringValue = ((String) value).trim();
    if (isNullString(stringValue)) {
      return null;
    }

    Class<?> clazz = null;
    try {
      clazz = Class.forName(type);
    } catch (ClassNotFoundException e) {
      // no need to deal
    }

    if (clazz != null && Map.class.isAssignableFrom(clazz)) {
      try {
        return parseJsonStringToMap(stringValue);
      } catch (IOException e) {
        log.error("It is not a valid JSON string. {}", e.getMessage());
        Throwables.propagate(e);
      }
    }

    if (clazz != null && List.class.isAssignableFrom(clazz)) {
      if (stringValue.startsWith("[")) {
        try {
          return parseJsonStringToList(stringValue);
        } catch (IOException e) {
          log.error("It is not a valid JSON string. {}", e.getMessage());
        }
      } else {
        return SPLITTER.splitToList(stringValue);
      }
    }

    return stringValue;
  }

  private boolean isNullString(String value) {
    return NULL_STRING.equalsIgnoreCase(value);
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
