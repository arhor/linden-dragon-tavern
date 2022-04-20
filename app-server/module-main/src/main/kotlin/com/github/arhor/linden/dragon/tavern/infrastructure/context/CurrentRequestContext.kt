package com.github.arhor.linden.dragon.tavern.infrastructure.context;

import java.util.UUID

interface CurrentRequestContext {

    var requestId: UUID?

    fun isExceptionLogged(throwable: Throwable): Boolean

    fun setExceptionBeenLogged(throwable: Throwable)
}
