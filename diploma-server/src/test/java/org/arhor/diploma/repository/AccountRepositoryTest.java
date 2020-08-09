package org.arhor.diploma.repository;

import net.bytebuddy.utility.RandomString;
import org.arhor.diploma.TestExecutionContext;
import org.arhor.diploma.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.arhor.diploma.TestUtils.generateAccountWithFilledFields;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class AccountRepositoryTest extends TestExecutionContext {

  @Autowired
  private AccountRepository repository;

  @Test
  @Transactional
  void shouldSoftlyDeleteAccount() {
    // given
    final var account = repository.save(generateAccountWithFilledFields());
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
}
