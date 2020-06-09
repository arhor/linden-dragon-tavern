package org.arhor.diploma.service.action;

import org.arhor.diploma.repository.domain.DomainObject;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public interface GetEntities {

  static <E extends DomainObject<?>, D, P> List<D> apply(
      P page,
      Function<P, Iterable<E>> entitiesLookup,
      Function<E, D> resultMapper
  ) {
    return apply(() -> entitiesLookup.apply(page), resultMapper);
  }

  static <E extends DomainObject<?>, D> List<D> apply(
      Supplier<Iterable<E>> entitiesLookup,
      Function<E, D> resultMapper
  ) {
    return StreamSupport
        .stream(entitiesLookup.get().spliterator(), false)
        .map(resultMapper)
        .collect(toList());
  }
}
