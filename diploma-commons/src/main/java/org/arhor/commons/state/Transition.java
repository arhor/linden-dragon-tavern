package org.arhor.commons.state;

public interface Transition<F extends State, T extends State> {

  void translate(F from, T to);

}
