package org.arhor.commons.pattern.composite;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public interface Composite<T> {

  void execute(@Nonnull Consumer<T> action);

}
