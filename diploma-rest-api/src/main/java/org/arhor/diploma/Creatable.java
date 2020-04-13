package org.arhor.diploma;

import java.time.temporal.TemporalAccessor;

public interface Creatable<T extends TemporalAccessor> {

  T getCreated();

  void setCreated(T created);

  default void onCreate() {}
}
