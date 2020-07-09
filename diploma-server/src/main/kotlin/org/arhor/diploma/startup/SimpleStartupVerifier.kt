package org.arhor.diploma.startup

import org.arhor.diploma.util.ActionResult
import org.arhor.diploma.util.Failure
import org.arhor.diploma.util.SpringProfile
import org.arhor.diploma.util.Success
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class SimpleStartupVerifier : StartupVerifier {

  override val order: Int = 0

  override fun verify(): ActionResult<String> {
    return Success("Simple startup verification complete. It should be always first.")
  }
}