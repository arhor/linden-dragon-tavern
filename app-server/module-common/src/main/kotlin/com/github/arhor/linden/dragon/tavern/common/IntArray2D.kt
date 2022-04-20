package com.github.arhor.linden.dragon.tavern.common

import java.util.Objects
import java.util.StringJoiner

@Suppress("unused")
class IntArray2D private constructor(@JvmField val rows: Int, @JvmField val cols: Int) {

    private val data: IntArray

    init {
        require(rows >= 0) { "rows number must not be negative" }
        require(cols >= 0) { "cols number must not be negative" }
        data = IntArray(rows * cols)
    }

    operator fun get(row: Int, col: Int): Int {
        val index = index(row, col)
        return data[index]
    }

    operator fun set(row: Int, col: Int, value: Int) {
        val index = index(row, col)
        data[index] = value
    }

    inline fun forEach(consumer: (value: Int) -> Unit) {
        forEach { value, _, _, _ -> consumer(value) }
    }

    inline fun forEach(consumer: (value: Int, row: Int, col: Int) -> Unit) {
        forEach { value, row, col, _ -> consumer(value, row, col) }
    }

    inline fun forEach(consumer: (value: Int, row: Int, col: Int, array: IntArray2D) -> Unit) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                consumer(this[row, col], row, col, this)
            }
        }
    }

    private fun index(row: Int, col: Int): Int {
        Objects.checkIndex(row, rows)
        Objects.checkIndex(col, cols)
        return (cols * row) + col
    }

    override fun toString(): String {
        val result = StringJoiner("\n")
        for (row in 0 until rows) {
            val line = StringJoiner(" ")
            for (col in 0 until cols) {
                line.add(this[row, col].toString())
            }
            result.add(line.toString())
        }
        return result.toString()
    }

    companion object {
        @JvmStatic
        val EMPTY = IntArray2D(0, 0)

        @JvmStatic
        fun create(rows: Int, cols: Int): IntArray2D {
            return if (rows != 0 && cols != 0) {
                IntArray2D(rows, cols)
            } else {
                EMPTY
            }
        }

        @JvmStatic
        inline fun create(rows: Int, cols: Int, init: (row: Int, col: Int) -> Int): IntArray2D {
            return create(rows, cols).also {
                for (row in 0 until rows) {
                    for (col in 0 until cols) {
                        it[row, col] = init(row, col)
                    }
                }
            }
        }

        @JvmStatic
        fun create(rows: Int, cols: Int, initial: Int): IntArray2D {
            return create(rows, cols) { _, _ -> initial }
        }
    }
}
