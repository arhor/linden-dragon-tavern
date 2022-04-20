package com.github.arhor.linden.dragon.tavern.data.persistence.converter

import com.github.arhor.linden.dragon.tavern.common.BitSet64
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

@ReadingConverter
class BitSet64ReadingConverter : Converter<Long, BitSet64> {

    override fun convert(source: Long): BitSet64 {
        return source.let(BitSet64::fromNumber)
    }
}

@WritingConverter
class BitSet64WritingConverter : Converter<BitSet64, Long> {

    override fun convert(source: BitSet64): Long {
        return source.valuesHolder
    }
}
