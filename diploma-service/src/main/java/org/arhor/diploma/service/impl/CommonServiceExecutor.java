package org.arhor.diploma.service.impl;

import lombok.RequiredArgsConstructor;
import org.arhor.diploma.repository.domain.DomainObject;
import org.arhor.diploma.service.exception.EntityNotFoundException;
import org.arhor.diploma.repository.BaseRepository;
import org.arhor.commons.util.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class CommonServiceExecutor {

  private final Converter converter;

  @Nonnull
  public <E extends DomainObject<K>, D, K> D getById(
      BaseRepository<E, K> repository,
      Class<D> dtoClass,
      K id) {
    return getOne(
        repository::findById,
        entity -> converter.convert(entity, dtoClass),
        id
    ).orElseThrow(
        notFound(dtoClass, "id", id)
    );
  }

  @Nonnull
  public <E extends DomainObject<K>, D, K> List<D> getAll(
      BaseRepository<E, K> repository,
      Class<D> dtoClass) {
    return getList(
        repository::findAll,
        entity -> converter.convert(entity, dtoClass)
    );
  }

  private <E extends DomainObject<K>, D, K>
  Optional<D> getOne(
      Function<K, Optional<E>> entityLookup,
      Function<E, D> resultMapper,
      K id) {
    return entityLookup.apply(id).map(resultMapper);
  }

  private <E extends DomainObject<K>, D, K>
  List<D> getList(
      Supplier<Collection<E>> entitiesLookup,
      Function<E, D> resultMapper) {
    return entitiesLookup.get().stream().map(resultMapper).collect(toList());
  }

  private
  Supplier<RuntimeException> notFound(Class<?> dtoClass, String param, Object value) {
    return notFound(
        parseEntityName(dtoClass),
        param,
        value);
  }

  private
  Supplier<RuntimeException> notFound(String entity, String param, Object value) {
    return () -> new EntityNotFoundException(entity, param, value);
  }

  private String parseEntityName(Class<?> dtoClass) {
    return dtoClass.getSimpleName().replaceAll("(DTO|Dto)", "");
  }
}
