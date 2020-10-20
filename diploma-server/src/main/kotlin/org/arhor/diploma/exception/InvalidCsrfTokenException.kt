package org.arhor.diploma.exception

class InvalidCsrfTokenException(
    val expected: String,
    val actual: String
) : CustomCsrfException("CSRF token is present, but does not match cookie value")