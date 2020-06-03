package org.arhor.commons.state;

public interface StateMachine<T extends Enum<T> & State> {

  static <T extends Enum<T> & State> StateMachine<T> build() {
    return null;
  }
}
