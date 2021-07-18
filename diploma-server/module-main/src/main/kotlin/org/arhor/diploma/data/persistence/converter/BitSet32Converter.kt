package org.arhor.diploma.data.persistence.converter

import org.arhor.diploma.commons.BitSet32
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

@ReadingConverter
class BitSet32ReadingConverter : Converter<Int, BitSet32> {

    override fun convert(source: Int): BitSet32 {
        return BitSet32.fromNumber(source)
    }
}

@WritingConverter
class BitSet32WritingConverter : Converter<BitSet32, Int> {

    override fun convert(source: BitSet32): Int {
        return source.valuesHolder
    }
}