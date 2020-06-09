package org.arhor.diploma.service.action;

import org.arhor.diploma.domain.DomainObject;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface GetEntity {

  static <E extends DomainObject<?>, D, K> Optional<D> apply(
      K key,
      Function<K, Optional<E>> entityLookup,
      Function<E, D> resultMapper
  ) {
    return apply(() -> entityLookup.apply(key), resultMapper);
  }

  static <E extends DomainObject<?>, D> Optional<D> apply(
      Supplier<Optional<E>> entityLookup,
      Function<E, D> resultMapper
  ) {
    return entityLookup.get().map(resultMapper);
  }
}
