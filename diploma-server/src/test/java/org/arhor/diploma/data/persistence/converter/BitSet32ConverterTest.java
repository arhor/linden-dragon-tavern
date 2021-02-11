package org.arhor.diploma.data.persistence.converter;

import org.arhor.diploma.commons.BitSet32;
import org.arhor.diploma.testutils.RandomParameter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({RandomParameter.Resolver.class})
class BitSet32ConverterTest {

    private static BitSet32Converter converterUnderTest;

    @BeforeAll
    static void setUp() {
        converterUnderTest = new BitSet32Converter();
    }

    @AfterAll
    static void tearDown() {
        converterUnderTest = null;
    }

    @Test
    void shouldCorrectlySerializeAndDeserializeValue(@RandomParameter final int valueBefore) {
        // when
        BitSet32 bitSet32 = converterUnderTest.convertToEntityAttribute(valueBefore);
        Integer valueAfter = converterUnderTest.convertToDatabaseColumn(bitSet32);

        // then
        assertThat(bitSet32).isNotNull();
        assertThat(valueAfter).isEqualTo(valueBefore);
    }
}