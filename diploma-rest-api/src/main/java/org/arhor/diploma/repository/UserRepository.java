package org.arhor.diploma.repository;

import org.arhor.diploma.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.arhor.diploma.Constants.CACHE_USER_BY_USERNAME;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.deleted = false AND u.username = :username")
  @Cacheable(cacheNames = CACHE_USER_BY_USERNAME, key = "#username")
  Optional<User> findByUsername(String username);

  @Override
  @CacheEvict(cacheNames = CACHE_USER_BY_USERNAME, key = "#user.username")
  void delete(@NonNull User user);
}
