package org.arhor.diploma.data.domain;

import java.time.temporal.TemporalAccessor;

public interface Updatable<T extends TemporalAccessor> {

  T getUpdated();

  void setUpdated(T updated);

  default void onUpdate() {}
}
