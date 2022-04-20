package com.github.arhor.linden.dragon.tavern.data.persistence

import com.github.arhor.linden.dragon.tavern.data.persistence.converter.BitSet64ReadingConverter
import com.github.arhor.linden.dragon.tavern.data.persistence.converter.BitSet64WritingConverter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(com.github.arhor.linden.dragon.tavern.testutils.RandomParameter.Resolver::class)
internal class BitSet64ConverterTest {

    @Test
    fun `should correctly serialize and deserialize value`(
        // given
        @com.github.arhor.linden.dragon.tavern.testutils.RandomParameter valueBefore: Long
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
