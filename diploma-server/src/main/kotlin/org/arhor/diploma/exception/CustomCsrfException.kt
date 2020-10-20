package org.arhor.diploma.exception

import org.springframework.security.access.AccessDeniedException

abstract class CustomCsrfException(msg: String) : AccessDeniedException(msg)