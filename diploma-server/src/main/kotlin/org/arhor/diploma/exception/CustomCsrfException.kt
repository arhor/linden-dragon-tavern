package org.arhor.diploma.exception

import org.springframework.security.access.AccessDeniedException

sealed class CustomCsrfException(msg: String) : AccessDeniedException(msg)

class MissingCsrfTokenException : CustomCsrfException("CSRF token is missing")

class InvalidCsrfTokenException(
    val expected: String,
    val actual: String
) : CustomCsrfException("CSRF token is present, but does not match cookie value")