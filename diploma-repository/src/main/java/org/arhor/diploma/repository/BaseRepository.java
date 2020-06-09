package org.arhor.diploma.repository;

import org.arhor.diploma.repository.domain.DomainObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends DomainObject<K>, K> extends JpaRepository<T, K> {

  @NonNull
  @Override
  @Transactional(readOnly = true)
  @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = false")
  List<T> findAll();

  @NonNull
  @Override
  @Transactional(readOnly = true)
  @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = false")
  Page<T> findAll(@NonNull Pageable pageable);

  @NonNull
  @Override
  @Transactional(readOnly = true)
  @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = false")
  List<T> findAll(@NonNull Sort sort);

  @NonNull
  @Override
  @Transactional(readOnly = true)
  @Query("SELECT e FROM #{#entityName} e WHERE e.id in :ids AND e.deleted = false")
  List<T> findAllById(@NonNull Iterable<K> ids);

  @NonNull
  @Override
  @Transactional(readOnly = true)
  @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.deleted = false")
  Optional<T> findById(@NonNull K id);

  @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = true")
  @Transactional(readOnly = true)
  List<T> findDeleted();

  @Override
  @Transactional(readOnly = true)
  @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.deleted = false")
  long count();

  @Override
  @Transactional(readOnly = true)
  default boolean existsById(@NonNull K id) {
    return findById(id).isPresent();
  }

  @Override
  @Modifying
  @Transactional
  @Query("UPDATE #{#entityName} e SET e.deleted = true WHERE e.id = :id")
  void deleteById(@NonNull K id);

  @Override
  @Transactional
  default void delete(T entity) {
    deleteById(entity.getId());
  }

  @Override
  @Transactional
  default void deleteAll(Iterable<? extends T> entities) {
    entities.forEach(this::delete);
  }

  @Override
  @Modifying
  @Transactional
  @Query("UPDATE #{#entityName} e SET e.deleted = true")
  void deleteAll();
}
