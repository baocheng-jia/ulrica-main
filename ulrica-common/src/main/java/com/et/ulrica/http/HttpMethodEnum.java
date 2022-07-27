package com.et.ulrica.http;

public enum HttpMethodEnum {
  GET("GET"),
  POST("POST"),
  PUT("PUT"),
  DELETE("DELETE"),
  ;


  private String methodName;

  HttpMethodEnum(String methodName) {
    this.methodName = methodName;
  }

  public String getMethodName() {
    return methodName;
  }
}
