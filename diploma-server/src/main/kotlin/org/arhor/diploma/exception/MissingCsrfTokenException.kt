package org.arhor.diploma.exception

class MissingCsrfTokenException : CustomCsrfException("CSRF token is missing")