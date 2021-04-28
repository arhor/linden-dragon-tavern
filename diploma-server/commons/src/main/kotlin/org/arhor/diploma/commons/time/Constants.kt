@file:Suppress("UNUSED")

package org.arhor.diploma.commons.time

/*
 * These constants supposed to be used to create human-readable values for
 * annotations, since such values must be a compile-time constants and
 * methods/functions cannot be used for such purposes.
 */

// @formatter:off
const val MILLISECOND = 1L
const val SECOND      = MILLISECOND * 1000L
const val MINUTE      = SECOND * 60L
const val HOUR        = MINUTE * 60L
const val DAY         = HOUR * 24L
// @formatter:on
