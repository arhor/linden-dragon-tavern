package org.arhor.diploma.core

class BitSet32 {

    private var values: Int = 0

    operator fun set(index: Int, value: Boolean) {
        ensureSafeAccess(index)
        values = if (value) {
            values or (1 shl index)
        } else {
            values and (1 shl index).inv()
        }
    }

    operator fun get(index: Int): Boolean {
        ensureSafeAccess(index)
        return (values and (1 shl index)) != 0
    }

    private fun ensureSafeAccess(index: Int) {
        if (index < 0 || index >= 32) throw IndexOutOfBoundsException()
    }
}