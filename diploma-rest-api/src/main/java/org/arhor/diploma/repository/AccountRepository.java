package org.arhor.diploma.repository;

import org.arhor.diploma.domain.Account;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.arhor.diploma.Constants.CACHE_ACCOUNT_BY_USERNAME;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {

  @Query("SELECT a FROM Account a WHERE a.deleted = false AND a.username = :username")
  @Cacheable(cacheNames = CACHE_ACCOUNT_BY_USERNAME, key = "#username")
  Optional<Account> findByUsername(String username);

  @Override
  @CacheEvict(cacheNames = CACHE_ACCOUNT_BY_USERNAME, key = "#account.username")
  void delete(@NonNull Account account);
}
