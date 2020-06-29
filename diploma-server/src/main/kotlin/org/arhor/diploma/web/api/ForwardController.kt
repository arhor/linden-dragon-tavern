package org.arhor.diploma.web.api

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ForwardController {

  // Match everything without a suffix (so not a static resource)
  // Forward to home page so that route is preserved.
  @GetMapping(API_PATH, WEBSOCKET_PATH)
  fun forward() = "forward:/"

  companion object {
    private const val API_PATH = "/{path:[^\\.]*}"
    private const val WEBSOCKET_PATH = "/{path:^(?!websocket).*}/**/{path:[^\\.]*}"

    // "/**/{path:[^.]*}"
  }
}
