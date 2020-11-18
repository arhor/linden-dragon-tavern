package org.arhor.diploma.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

internal inline fun <T> T?.asBoolean(test: (T) -> Boolean): Boolean {
    return this?.let(test) ?: false
}

internal inline fun <reified T> createLogger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}