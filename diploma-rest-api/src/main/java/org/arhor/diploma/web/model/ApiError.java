package org.arhor.diploma.web.model;

public final class ApiError {

  private final Type type;
  private final int code;
  private final String msg;

  public ApiError(Type type, int code, String msg) {
    this.type = type != null ? type : Type.UNKNOWN;
    this.code = code;
    this.msg = msg;
  }

  enum Type {
    COMMON,
    UNKNOWN
  }
}
