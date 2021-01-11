package org.arhor.diploma.commons

import java.util.*

sealed class CompactBitSet : Iterable<Boolean> {

    abstract fun size(): Int

    abstract operator fun get(index: Int): Boolean

    abstract operator fun set(index: Int, value: Boolean)

    override fun iterator(): BooleanIterator {
        return object : BooleanIterator() {
            private var index = 0
            override fun hasNext() = index < size()
            override fun nextBoolean() = get(index++)

        }
    }

    override fun toString(): String {
        val result = StringJoiner(",", "${this::class.simpleName}(", ")")
        for (i in 0 until size()) {
            result.add(if (this[i]) "1" else "0")
        }
        return result.toString()
    }

    protected fun ensureSafeAccess(index: Int) {
        if (index < 0 || index >= size()) {
            throw IndexOutOfBoundsException("Index $index is out of range [0, ${size()})")
        }
    }
}

class BitSet32 : CompactBitSet() {

    var valuesHolder: Int = 0
        private set

    override fun size(): Int = CAPACITY

    override operator fun set(index: Int, value: Boolean) {
        ensureSafeAccess(index)
        valuesHolder = if (value) {
            valuesHolder or (1 shl index)
        } else {
            booleanArrayOf(true)
            valuesHolder and (1 shl index).inv()
        }
    }

    override operator fun get(index: Int): Boolean {
        ensureSafeAccess(index)
        return (valuesHolder and (1 shl index)) != 0
    }

    companion object {
        const val CAPACITY = 32

        @JvmStatic
        fun fromNumber(number: Int): BitSet32 {
            return BitSet32().also { it.valuesHolder = number }
        }
    }
}

class BitSet64 : CompactBitSet() {

    var valuesHolder: Long = 0L
        private set

    override fun size(): Int = CAPACITY

    override operator fun set(index: Int, value: Boolean) {
        ensureSafeAccess(index)
        valuesHolder = if (value) {
            valuesHolder or (1L shl index)
        } else {
            booleanArrayOf(true)
            valuesHolder and (1L shl index).inv()
        }
    }

    override operator fun get(index: Int): Boolean {
        ensureSafeAccess(index)
        return (valuesHolder and (1L shl index)) != 0L
    }

    companion object {
        const val CAPACITY = 64

        @JvmStatic
        fun fromNumber(number: Long): BitSet64 {
            return BitSet64().also { it.valuesHolder = number }
        }
    }
}