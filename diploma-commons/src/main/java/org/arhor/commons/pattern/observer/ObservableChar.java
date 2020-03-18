package org.arhor.commons.pattern.observer;

public interface ObservableChar extends ObservableVal<Character> {

  char getValue();

  void setValue(char value);
}
