package org.arhor.diploma.security

import org.arhor.diploma.util.Common
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.*

/**
 * Implementation of [AuditorAware] based on Spring Security.
 */
@Component
class SpringSecurityAuditorAware : AuditorAware<String> {
  override fun getCurrentAuditor(): Optional<String> {
    return Optional.of(currentUserLogin() ?: Common.SYSTEM_ACCOUNT)
  }
}