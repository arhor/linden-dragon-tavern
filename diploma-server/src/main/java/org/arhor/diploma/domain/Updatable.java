package org.arhor.diploma.domain;

import java.time.temporal.TemporalAccessor;

@SuppressWarnings({"unused"})
public interface Updatable<T extends TemporalAccessor> {

  T getUpdated();

  void setUpdated(T updated);

  default void onUpdate() { /* do nothing by default */ }
}
