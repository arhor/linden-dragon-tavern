package org.arhor.diploma.aspect

import org.arhor.diploma.data.persistence.repository.BaseRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.invoke.MethodHandles

@Aspect
@Component
class AuditAspect {

    @Around(value = "(saveEvent() || deleteEvent()) && target(repository)", argNames = "repository")
    fun handlePersistenceEvent(joinPoint: ProceedingJoinPoint, repository: BaseRepository<*, *>): Any {
        val name = joinPoint.signature.name

        if (log.isDebugEnabled) {
            if (name.startsWith("save")) {
                log.debug("[SAVE] event intercepted")
            } else if (name.startsWith("delete")) {
                log.debug("[DELETE] event intercepted")
            }
        }

        return joinPoint.proceed()
    }

    @Pointcut("execution(* org.arhor.diploma.data.persistence.repository.*Repository.save*(..))")
    fun saveEvent() {
        /* no-op */
    }

    @Pointcut("execution(* org.arhor.diploma.data.persistence.repository.*Repository.delete*(..))")
    fun deleteEvent() {
        /* no-op */
    }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}