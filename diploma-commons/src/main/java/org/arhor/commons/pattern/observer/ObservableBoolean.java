package org.arhor.commons.pattern.observer;

public interface ObservableBoolean extends ObservableVal<Boolean> {

  void setValue(boolean value);

  boolean getValue();
}
