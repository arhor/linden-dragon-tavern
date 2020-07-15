package org.arhor.diploma.util

internal inline fun <T> T?.asBoolean(test: (T) -> Boolean): Boolean {
    return this?.let(test) ?: false
}