package org.arhor.diploma.web.model;

import lombok.Data;

import java.util.List;

@Data
public final class MessageResponse {

  private final List<Message> messages;

  private MessageResponse(Message[] messages) {
    this.messages = List.of(messages);
  }

  public static MessageResponse of(Message... messages) {
    return new MessageResponse(messages);
  }
}
