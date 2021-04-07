package org.arhor.diploma.aspect

import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Aspect
@Component
class LoggingAspect {

    @Before("execution(* *(.., @org.springframework.web.bind.annotation.ExceptionHandler (*), ..))")
    fun logExceptionHandling(joinPoint: JoinPoint) {
        for (arg in joinPoint.args) {
            if (arg is Throwable) {
                logger.error(arg.message, arg)
            }
        }
    }
}
