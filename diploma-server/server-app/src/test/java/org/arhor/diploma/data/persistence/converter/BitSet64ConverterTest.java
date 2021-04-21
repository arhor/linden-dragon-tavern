package org.arhor.diploma.data.persistence.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.arhor.diploma.commons.BitSet64;
import org.arhor.diploma.testutils.RandomParameter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({RandomParameter.Resolver.class})
class BitSet64ConverterTest {

    private static BitSet64ReadingConverter readingConverter;
    private static BitSet64WritingConverter writingConverter;

    @BeforeAll
    static void setUp() {
        readingConverter = new BitSet64ReadingConverter();
        writingConverter = new BitSet64WritingConverter();
    }

    @AfterAll
    static void tearDown() {
        readingConverter = null;
        writingConverter = null;
    }

    @Test
    void shouldCorrectlySerializeAndDeserializeValue(@RandomParameter final long valueBefore) {
        // when
        BitSet64 bitSet64 = readingConverter.convert(valueBefore);
        Long valueAfter = writingConverter.convert(bitSet64);

        // then
        assertThat(bitSet64).isNotNull();
        assertThat(valueAfter).isEqualTo(valueBefore);
    }
}