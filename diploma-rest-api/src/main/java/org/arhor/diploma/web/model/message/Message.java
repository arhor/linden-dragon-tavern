package org.arhor.diploma.web.model.message;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({"code", "severity", "text", "details"})
public final class Message {

  private final int code;
  private final Severity severity;
  private final String text;
  private final List<String> details;

  public static Builder info() {
    return new Builder(Severity.INFO);
  }

  public static Builder warn() {
    return new Builder(Severity.WARN);
  }

  public static Builder error() {
    return new Builder(Severity.ERROR);
  }

  private static final class Builder {

    private final Severity severity;

    private int code;
    private String text;
    private List<String> details;

    private Builder(Severity severity) {
      this.severity = severity;
    }

    public Message build() {
      return new Message(code, severity, text, details);
    }

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
}
