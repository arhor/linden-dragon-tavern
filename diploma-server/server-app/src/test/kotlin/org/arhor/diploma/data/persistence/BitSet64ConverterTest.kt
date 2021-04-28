package org.arhor.diploma.data.persistence

import org.arhor.diploma.data.persistence.converter.BitSet64ReadingConverter
import org.arhor.diploma.data.persistence.converter.BitSet64WritingConverter
import org.arhor.diploma.testutils.RandomParameter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(RandomParameter.Resolver::class)
internal class BitSet64ConverterTest {

    @Test
    fun `should correctly serialize and deserialize value`(
        // given
        @RandomParameter valueBefore: Long
    ) {
        // when
        val bitSet64 = readingConverter.convert(valueBefore)
        val valueAfter = writingConverter.convert(bitSet64)

        // then
        assertThat(bitSet64).isNotNull
        assertThat(valueAfter).isEqualTo(valueBefore)
    }

    companion object {
        private lateinit var readingConverter: BitSet64ReadingConverter
        private lateinit var writingConverter: BitSet64WritingConverter

        @BeforeAll
        @JvmStatic
        fun setUp() {
            readingConverter = BitSet64ReadingConverter()
            writingConverter = BitSet64WritingConverter()
        }
    }
}