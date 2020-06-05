package org.arhor.diploma.data.repository;

import net.bytebuddy.utility.RandomString;
import org.arhor.diploma.TestExecutionContext;
import org.arhor.diploma.data.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
    final var account = repository.save(generateAccount());
    assertFalse(account.isDeleted());

    // when
    repository.delete(account);

    // then
    assertAll(
        () -> assertThat(account.getId()).isNotNull(),
        () -> assertThat(repository.findById(account.getId())).isEmpty(),
        () -> assertThat(repository.findDeleted()).contains(account)
    );
  }

  private static Account generateAccount() {
    final var account = new Account();

    account.setUsername(RandomString.make());
    account.setPassword(RandomString.make());
    account.setFirstName(RandomString.make());
    account.setLastName(RandomString.make());

    return account;
  }
}
