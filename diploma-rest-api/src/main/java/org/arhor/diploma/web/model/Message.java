package org.arhor.diploma.web.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({"code", "type", "text", "details"})
public abstract class Message {

  private final int code;
  private final String text;
  private final List<String> details;

  public static Builder error() {
    return new ErrorBuilder();
  }

  public abstract String getType();

  public abstract static class Builder {

    protected int code;
    protected String text;
    protected List<String> details;

    private Builder() {}

    public abstract Message build();

    public Builder withCode(int code) {
      this.code = code;
      return this;
    }

    public Builder withText(String text) {
      this.text = text;
      return this;
    }

    public Builder withDetails(String... details) {
      this.details = List.of(details);
      return this;
    }
  }

  private static class ErrorBuilder extends Builder {

    private ErrorBuilder() {}

    @Override
    public Message build() {
      return new ErrorMessage(code, text, details);
    }
  }
}
