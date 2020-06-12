package org.arhor.diploma.web.api

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ForwardController {

  // Match everything without a suffix (so not a static resource)
  // Forward to home page so that route is preserved.
  @GetMapping(path = ["/**/{path:[^.]*}"])
  fun forward() = "forward:/"
}
