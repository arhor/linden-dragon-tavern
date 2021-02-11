package org.arhor.diploma.data.persistence.converter

import org.arhor.diploma.commons.BitSet64
import javax.persistence.AttributeConverter

class BitSet64Converter : AttributeConverter<BitSet64, Long> {

    override fun convertToDatabaseColumn(attribute: BitSet64?): Long? {
        return attribute?.valuesHolder
    }

    override fun convertToEntityAttribute(dbData: Long?): BitSet64? {
        return dbData?.let(BitSet64::fromNumber)
    }
}