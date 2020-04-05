package org.arhor.diploma.web.model;

import lombok.Data;

@Data(staticConstructor = "of")
public final class Details implements Message.Details {

  private final String text;
}
