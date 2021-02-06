package org.arhor.diploma.data.persistence.domain.extension;

import org.arhor.diploma.testutils.RandomParameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;

@ExtendWith({RandomParameter.Resolver.class})
class ExtraDataHolderTest {

    @Test
    void should() {
        var dataHolder = new ExtraDataHolder();

        System.out.println(dataHolder.getState());

        dataHolder.setDataProvider(HashMap::new);

        System.out.println(dataHolder.getNames());

        System.out.println(dataHolder.getState());

        dataHolder.set("name", "Max");

        System.out.println(dataHolder.getState());

        System.out.println(dataHolder.getNames());

        System.out.println(dataHolder.getState());
    }
}
