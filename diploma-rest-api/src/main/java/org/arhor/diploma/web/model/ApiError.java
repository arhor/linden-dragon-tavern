package org.arhor.diploma.web.model;

public final class ApiError {

  enum Type {
    COMMON,
    WRONG_ARGUMENT_TYPE,
    UNKNOWN,
  }

  private final Type type;
  private final int code;
  private final String msg;

  public ApiError(Type type, int code, String msg) {
    this.type = type != null ? type : Type.UNKNOWN;
    this.code = code;
    this.msg = msg;
  }

  public ApiError(int code, String msg) {
    this(Type.UNKNOWN, code, msg);
  }

  public Type getType() {
    return type;
  }

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
}
