package org.arhor.diploma;

import java.time.temporal.TemporalAccessor;

public interface Updatable<T extends TemporalAccessor> {

  T getUpdated();

  void setUpdated(T updated);

  default void onUpdate() {}
}
