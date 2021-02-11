package org.arhor.diploma.data.persistence.converter

import org.arhor.diploma.commons.BitSet32
import javax.persistence.AttributeConverter

class BitSet32Converter : AttributeConverter<BitSet32, Int> {

    override fun convertToDatabaseColumn(attribute: BitSet32?): Int? {
        return attribute?.valuesHolder
    }

    override fun convertToEntityAttribute(dbData: Int?): BitSet32? {
        return dbData?.let(BitSet32::fromNumber)
    }
}