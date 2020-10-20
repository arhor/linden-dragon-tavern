package org.arhor.diploma

object CsrfUtils {
    const val CSRF_HEADER_NAME = "x-csrf-token"

    const val CSRF_COOKIE_NAME = "csrf-token"

    val SAFE_METHODS = arrayOf("GET", "HEAD", "OPTIONS", "TRACE")
}