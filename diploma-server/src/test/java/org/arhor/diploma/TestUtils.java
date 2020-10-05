package org.arhor.diploma;

import net.bytebuddy.utility.RandomString;
import org.arhor.diploma.data.persist.domain.Account;

import java.util.Random;

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

    public static String randomString() {
        return RandomString.make();
    }

    public static String randomString(int length) {
        return RandomString.make(length);
    }

    public static int randomInt(int bound) {
        return new Random().nextInt(bound);
    }
}
