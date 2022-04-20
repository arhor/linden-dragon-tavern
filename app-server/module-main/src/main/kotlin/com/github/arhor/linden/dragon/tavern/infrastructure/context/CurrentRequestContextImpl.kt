package com.github.arhor.linden.dragon.tavern.infrastructure.context

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode.INTERFACES
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext
import java.util.UUID

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = INTERFACES)
class CurrentRequestContextImpl : CurrentRequestContext {

    override var requestId: UUID? = UUID.randomUUID()

    private lateinit var loggedExceptions: MutableSet<Throwable>

    @Override
    override fun isExceptionLogged(throwable: Throwable): Boolean {
        return ::loggedExceptions.isInitialized && loggedExceptions.contains(throwable)
    }

    @Override
    override fun setExceptionBeenLogged(throwable: Throwable) {
        if (!::loggedExceptions.isInitialized) {
            loggedExceptions = HashSet()
        }
        loggedExceptions.add(throwable)
    }
}
