package org.arhor.diploma.web.model;

import lombok.Data;

import java.util.List;

/**
 * Represents general message response from the server.
 *
 * JSON form:
 *
 * {
 *   messages: [
 *     {
 *       code: 400,
 *       type: error,
 *       text: "Validation failed",
 *       details: [
 *         "field 'username' should not be blank",
 *         "field 'password' should not be null"
 *       ]
 *     }
 *   ]
 * }
 *
 * @author Maksim Buryshynets
 */
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
