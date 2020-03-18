package org.arhor.commons.function;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface Functor<A, B> {

  Function<UnaryOperator<B>, UnaryOperator<A>> flatMap(Function<A, B> f);

}
