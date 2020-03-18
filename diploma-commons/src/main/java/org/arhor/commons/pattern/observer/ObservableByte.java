package org.arhor.commons.pattern.observer;

public interface ObservableByte extends ObservableVal<Byte> {

  byte getValue();

  void setValue(byte value);
}
