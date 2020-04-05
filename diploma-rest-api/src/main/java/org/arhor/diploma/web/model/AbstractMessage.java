package org.arhor.diploma.web.model;

import lombok.Data;

import java.util.List;

@Data
abstract class AbstractMessage implements Message {

  private final int code;
  private final String text;
  private final List<Details> details;

  protected AbstractMessage(int code, String text, List<Details> details) {
    this.code = code;
    this.text = text;
    this.details = details;
  }

  public abstract static class Builder {

    protected int code;
    protected String text;
    protected List<Details> details;

    public abstract Message build();

    public Builder withCode(int code) {
      this.code = code;
      return this;
    }

    public Builder withText(String text) {
      this.text = text;
      return this;
    }

    public Builder withDetails(Details... details) {
      this.details = List.of(details);
      return this;
    }
  }

  public static class ErrorBuilder extends Builder {
    @Override
    public Message build() {
      return new ErrorMessage(this.code, this.text, this.details);
    }
  }
}
