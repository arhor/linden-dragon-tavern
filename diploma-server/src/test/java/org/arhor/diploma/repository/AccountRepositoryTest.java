package org.arhor.diploma.repository;

import net.bytebuddy.utility.RandomString;
import org.arhor.diploma.TestExecutionContext;
import org.arhor.diploma.domain.Account;
import org.arhor.diploma.domain.core.Deletable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class AccountRepositoryTest extends TestExecutionContext {

  @Autowired
  private AccountRepository repository;

  @Test
  @Transactional
  public void shouldSoftlyDeleteAccount() {
    // given
    final Account account = repository.save(generateAccount());
    assertFalse(account.isDeleted());

    // when
    repository.delete(account);

    // then
    assertAll(
        () -> assertThat(account.getId()).isNotNull(),
        () -> assertThat(repository.findById(Objects.requireNonNull(account.getId()))).isEmpty(),
        () -> assertThat(repository.findDeleted()).contains(account)
    );
  }

  private static Account generateAccount() {
    final Account account = new Account();

    account.setUsername(RandomString.make());
    account.setPassword(RandomString.make());
    account.setFirstName(RandomString.make());
    account.setLastName(RandomString.make());

    return account;
  }
}
