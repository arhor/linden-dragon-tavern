package org.arhor.diploma;

import net.bytebuddy.utility.RandomString;
import org.arhor.diploma.domain.Account;

public class TestUtils {

    public static Account generateAccountWithFilledFields() {
        final var account = new Account();

        account.setUsername(RandomString.make());
        account.setPassword(RandomString.make());
        account.setFirstName(RandomString.make());
        account.setLastName(RandomString.make());
        account.setEmail(RandomString.make());

        return account;
    }
}
