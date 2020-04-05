package org.arhor.diploma.web.model;

import java.util.List;

public final class ErrorMessage extends Message {

  ErrorMessage(int code, String text, List<String> details) {
    super(code, text, details);
  }

  @Override
  public final String getType() {
    return "ERROR";
  }
}
