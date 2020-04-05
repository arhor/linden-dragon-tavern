package org.arhor.diploma.web.model;

import java.util.List;

public interface Message {

  static AbstractMessage.Builder error() {
    return new AbstractMessage.ErrorBuilder();
  }

  int getCode();

  String getText();

  String getType();

  List<Details> getDetails();

  interface Details {
    String getText();
  }
}
