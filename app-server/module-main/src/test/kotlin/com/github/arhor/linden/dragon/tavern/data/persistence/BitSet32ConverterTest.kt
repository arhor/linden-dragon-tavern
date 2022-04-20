package com.github.arhor.linden.dragon.tavern.data.persistence

import com.github.arhor.linden.dragon.tavern.data.persistence.converter.BitSet32ReadingConverter
import com.github.arhor.linden.dragon.tavern.data.persistence.converter.BitSet32WritingConverter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(com.github.arhor.linden.dragon.tavern.testutils.RandomParameter.Resolver::class)
internal class BitSet32ConverterTest {

    @Test
    fun `should correctly serialize and deserialize value`(
        // given
        @com.github.arhor.linden.dragon.tavern.testutils.RandomParameter valueBefore: Int
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
