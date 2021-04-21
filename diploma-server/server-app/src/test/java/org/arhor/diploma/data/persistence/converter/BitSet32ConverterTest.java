package org.arhor.diploma.data.persistence.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.arhor.diploma.commons.BitSet32;
import org.arhor.diploma.testutils.RandomParameter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({RandomParameter.Resolver.class})
class BitSet32ConverterTest {

    private static BitSet32ReadingConverter readingConverter;
    private static BitSet32WritingConverter writingConverter;

    @BeforeAll
    static void setUp() {
        readingConverter = new BitSet32ReadingConverter();
        writingConverter = new BitSet32WritingConverter();
    }

    @AfterAll
    static void tearDown() {
        readingConverter = null;
        writingConverter = null;
    }

    @Test
    void shouldCorrectlySerializeAndDeserializeValue(@RandomParameter final int valueBefore) {
        // when
        BitSet32 bitSet32 = readingConverter.convert(valueBefore);
        Integer valueAfter = writingConverter.convert(bitSet32);

        // then
        assertThat(bitSet32).isNotNull();
        assertThat(valueAfter).isEqualTo(valueBefore);
    }
}