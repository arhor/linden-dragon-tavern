package org.arhor.diploma.web.security

import org.arhor.diploma.util.BasicAuthorities
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

/**
 * Get the login of the current user.
 *
 * @return the login of the current user.
 */
internal inline val currentUserLogin: String?
  get() {
    return SecurityContextHolder.getContext().authentication?.principal
        ?.let {
          when (it) {
            is UserDetails -> it.username
            is String -> it
            else -> null
          }
        }
  }

/**
 * Get the JWT of the current user.
 *
 * @return the JWT of the current user.
 */
internal inline val currentUserJWT: String?
  get() {
    return SecurityContextHolder.getContext().authentication?.credentials
        ?.takeIf { it is String }
        ?.let { it as String }
  }

/**
 * Check if a user is authenticated.
 *
 * @return true if the user is authenticated, false otherwise.
 */
internal inline val isAuthenticated: Boolean
  get() {
    return currUserAuthorities.none { BasicAuthorities.ANONYMOUS.role == it }
  }

/**
 * If the current user has a specific authority (security role).
 *
 *
 * The name of this method comes from the `isUserInRole()` method in the Servlet API.
 *
 * @param authority the authority to check.
 * @return true if the current user has the authority, false otherwise.
 */
internal fun isCurrentUserInRole(authority: String): Boolean {
  return currUserAuthorities.any { authority == it }
}

private inline val currUserAuthorities: Collection<String>
  get() {
    return SecurityContextHolder.getContext().authentication?.authorities
        ?.map { it.authority }
        ?: emptyList()
  }
