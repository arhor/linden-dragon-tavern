package org.arhor.diploma.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwardController {

  // Match everything without a suffix (so not a static resource)
  @GetMapping(path = "/**/{path:[^.]*}")
  public String forward() {
    // Forward to home page so that route is preserved.
    return "forward:/";
  }
}
