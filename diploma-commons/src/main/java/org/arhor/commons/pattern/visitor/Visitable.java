package org.arhor.commons.pattern.visitor;

public interface Visitable<V> {

  void accept(Visitor<Visitable<V>> visitor);

}
