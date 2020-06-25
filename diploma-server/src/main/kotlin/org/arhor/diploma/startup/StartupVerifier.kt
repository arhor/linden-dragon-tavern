package org.arhor.diploma.startup

import org.arhor.diploma.util.ActionResult

interface StartupVerifier {

  fun verify(): ActionResult

}