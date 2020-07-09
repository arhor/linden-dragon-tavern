package org.arhor.diploma.startup

import org.arhor.diploma.util.ActionResult
import org.arhor.diploma.util.Failure
import org.arhor.diploma.util.SpringProfile
import org.arhor.diploma.util.Success
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ActiveProfilesVerifier(private val env: Environment) : StartupVerifier {

  override val order: Int = 1

  override fun verify(): ActionResult<String> {
    val activeProfiles = env.activeProfiles

    val isDev = activeProfiles.contains(SpringProfile.DEVELOPMENT)

    if (isDev && activeProfiles.contains(SpringProfile.PRODUCTION)) {
      return Failure(createError(SpringProfile.DEVELOPMENT, SpringProfile.PRODUCTION))
    }
    if (isDev && activeProfiles.contains(SpringProfile.CLOUD)) {
      return Failure(createError(SpringProfile.DEVELOPMENT, SpringProfile.CLOUD))
    }

    return Success("There are no mutually exclusive profiles.")
  }

  private fun createError(p1: String, p2: String): Throwable {
    return IllegalStateException("Profiles '${p1}' and '${p2}' should not run both at the same time!")
  }
}