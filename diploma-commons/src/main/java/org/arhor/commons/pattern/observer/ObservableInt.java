package org.arhor.commons.pattern.observer;

public interface ObservableInt extends ObservableVal<Integer> {

  void setValue(int value);

  int getValue();
}
