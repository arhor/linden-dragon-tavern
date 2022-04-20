package com.github.arhor.linden.dragon.tavern.infrastructure.aspect

import com.github.arhor.linden.dragon.tavern.infrastructure.context.CurrentRequestContext
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect(private val currentRequestContext: CurrentRequestContext) {

    @Around("webLayer() || serviceLayer() || persistenceLayer()")
    fun logMethodExecution(joinPoint: ProceedingJoinPoint): Any? {
        val log = joinPoint.componentLogger
        if (log.isDebugEnabled) {
            val requestId = currentRequestContext.requestId
            val signature = joinPoint.signature
            val signatureName = "${signature.declaringType.simpleName}.${signature.name}()"
            log.debug(
                "Request-ID: {}, Method: {}, Arguments: {}",
                requestId,
                signatureName,
                joinPoint.args.joinToString()
            )
            val result = joinPoint.proceed()
            log.debug(
                "Request-ID: {}, Method: {}, Result: {}",
                requestId,
                signatureName,
                result
            )
            return result
        } else {
            return joinPoint.proceed()
        }
    }

    @AfterThrowing(pointcut = "webLayer() || serviceLayer() || persistenceLayer()", throwing = "exception")
    fun logException(joinPoint: JoinPoint, exception: Throwable) {
        if (!currentRequestContext.isExceptionLogged(exception)) {
            joinPoint.componentLogger.error(
                "Request-ID: {}",
                currentRequestContext.requestId,
                exception
            )
            currentRequestContext.setExceptionBeenLogged(exception)
        }
    }

    @Pointcut(
        value = "execution(* dev.arhor.simple.todo.web.controller..*(..))" +
            " && within(@org.springframework.web.bind.annotation.RestController *)"
    )
    private fun webLayer() { /* no-op */ }

    @Pointcut(
        value = "execution(* dev.arhor.simple.todo.service..*(..))" +
            " && within(@org.springframework.stereotype.Service *)"
    )
    private fun serviceLayer() { /* no-op */ }

    @Pointcut(
        value = "execution(* dev.arhor.simple.todo.data..*(..))" +
            " && within(@org.springframework.stereotype.Repository *)"
    )
    private fun persistenceLayer() { /* no-op */ }

    private val JoinPoint.componentLogger: Logger
        get() = LoggerFactory.getLogger(signature.declaringTypeName)
}

