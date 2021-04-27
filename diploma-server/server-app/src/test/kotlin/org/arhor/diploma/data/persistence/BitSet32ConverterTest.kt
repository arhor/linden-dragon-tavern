package org.arhor.diploma.data.persistence

import org.arhor.diploma.data.persistence.converter.BitSet32ReadingConverter
import org.arhor.diploma.data.persistence.converter.BitSet32WritingConverter
import org.arhor.diploma.testutils.RandomParameter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(RandomParameter.Resolver::class)
class BitSet32ConverterTest {

    @Test
    fun `should correctly serialize and deserialize value`(
        // given
        @RandomParameter valueBefore: Int
    ) {
        // when
        val bitSet32 = readingConverter.convert(valueBefore)
        val valueAfter = writingConverter.convert(bitSet32)

        // then
        assertThat(bitSet32).isNotNull
        assertThat(valueAfter).isEqualTo(valueBefore)
    }

    companion object {
        private lateinit var readingConverter: BitSet32ReadingConverter
        private lateinit var writingConverter: BitSet32WritingConverter

        @BeforeAll
        @JvmStatic
        fun setUp() {
            readingConverter = BitSet32ReadingConverter()
            writingConverter = BitSet32WritingConverter()
        }
    }
}