package org.arhor.diploma.data.persistence.converter;

import org.arhor.diploma.commons.BitSet64;
import org.arhor.diploma.testutils.RandomParameter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({RandomParameter.Resolver.class})
class BitSet64ConverterTest {

    private static BitSet64Converter converterUnderTest;

    @BeforeAll
    static void setUp() {
        converterUnderTest = new BitSet64Converter();
    }

    @AfterAll
    static void tearDown() {
        converterUnderTest = null;
    }

    @Test
    void shouldCorrectlySerializeAndDeserializeValue(@RandomParameter final long valueBefore) {
        // when
        BitSet64 bitSet64 = converterUnderTest.convertToEntityAttribute(valueBefore);
        Long valueAfter = converterUnderTest.convertToDatabaseColumn(bitSet64);

        // then
        assertThat(bitSet64).isNotNull();
        assertThat(valueAfter).isEqualTo(valueBefore);
    }
}