package org.arhor.diploma.aspect;

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {

  companion object {
    @JvmStatic
    private val log: Logger = LoggerFactory.getLogger(LoggingAspect::class.java)
  }

  @Before("execution(* *(.., @org.springframework.web.bind.annotation.ExceptionHandler (*), ..))")
  fun logExceptionHandling(joinPoint: JoinPoint) {
    for (arg in joinPoint.args) {
      if (arg is Throwable) {
        log.error(arg.message, arg)
      }
    }
  }
}
