package org.arhor.commons.pattern.observer;

public interface ObservableFloat extends ObservableVal<Float> {

  float getValue();

  void setValue(float value);
}
