package org.arhor.diploma.aspect

import org.arhor.diploma.util.createLogger
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {

    @Before("execution(* *(.., @org.springframework.web.bind.annotation.ExceptionHandler (*), ..))")
    fun logExceptionHandling(joinPoint: JoinPoint) {
        for (arg in joinPoint.args) {
            if (arg is Throwable) {
                log.error(arg.message, arg)
            }
        }
    }

    companion object {
        @JvmStatic
        private val log = createLogger<LoggingAspect>()
    }
}
