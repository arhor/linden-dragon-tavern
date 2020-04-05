package org.arhor.diploma.web.model;

import java.util.List;

final class ErrorMessage extends AbstractMessage {

  ErrorMessage(int code, String text, List<Details> details) {
    super(code, text, details);
  }

  @Override
  public final String getType() {
    return "ERROR";
  }
}
