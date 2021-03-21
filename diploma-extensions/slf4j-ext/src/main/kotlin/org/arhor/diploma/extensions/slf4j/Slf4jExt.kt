@file:Suppress("unused")

package org.arhor.diploma.extensions.slf4j

import org.slf4j.Logger

typealias LazyMessage = () -> String

/**
 *
 */
inline fun Logger.error(msg: LazyMessage) {
    if (isErrorEnabled) {
        error(msg())
    }
}

/**
 *
 */
inline fun Logger.error(t: Throwable, msg: LazyMessage) {
    if (isErrorEnabled) {
        error(msg(), t)
    }
}

/**
 *
 */
inline fun Logger.warn(msg: LazyMessage) {
    if (isWarnEnabled) {
        warn(msg())
    }
}

/**
 *
 */
inline fun Logger.warn(t: Throwable, msg: LazyMessage) {
    if (isWarnEnabled) {
        warn(msg(), t)
    }
}

/**
 *
 */
inline fun Logger.info(msg: LazyMessage) {
    if (isInfoEnabled) {
        info(msg())
    }
}

/**
 *
 */
inline fun Logger.info(t: Throwable, msg: LazyMessage) {
    if (isInfoEnabled) {
        info(msg(), t)
    }
}

inline fun Logger.debug(msg: LazyMessage) {
    if (isDebugEnabled) {
        debug(msg())
    }
}

/**
 *
 */
inline fun Logger.debug(t: Throwable, msg: LazyMessage) {
    if (isDebugEnabled) {
        debug(msg(), t)
    }
}

/**
 *
 */
inline fun Logger.trace(msg: LazyMessage) {
    if (isTraceEnabled) {
        trace(msg())
    }
}

/**
 *
 */
inline fun Logger.trace(t: Throwable, msg: LazyMessage) {
    if (isTraceEnabled) {
        trace(msg(), t)
    }
}
