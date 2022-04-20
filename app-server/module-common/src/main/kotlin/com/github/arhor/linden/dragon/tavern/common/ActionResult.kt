package com.github.arhor.linden.dragon.tavern.common

@Suppress("unused")
sealed class ActionResult<out T> {

    abstract val isSuccess: Boolean
    abstract val isFailure: Boolean

    data class Success<out T>(val value: T? = null) : com.github.arhor.linden.dragon.tavern.common.ActionResult<T>() {
        override val isSuccess = true
        override val isFailure = false
    }

    data class Failure(val error: Throwable) : com.github.arhor.linden.dragon.tavern.common.ActionResult<Nothing>() {
        override val isSuccess = false
        override val isFailure = true
    }

    override fun toString(): String {
        return when (this) {
            is com.github.arhor.linden.dragon.tavern.common.ActionResult.Success -> value?.toString()
                ?: "success"
            is com.github.arhor.linden.dragon.tavern.common.ActionResult.Failure -> error.message
                ?: "failure"
        }
    }

    inline fun <R> map(f: (T?) -> R?): com.github.arhor.linden.dragon.tavern.common.ActionResult<R> {
        return when (this) {
            is com.github.arhor.linden.dragon.tavern.common.ActionResult.Success<T> -> com.github.arhor.linden.dragon.tavern.common.ActionResult.Companion.executeCatching {
                f(
                    value
                )
            }
            is com.github.arhor.linden.dragon.tavern.common.ActionResult.Failure -> this
        }
    }

    inline fun <R> flatMap(f: (T?) -> com.github.arhor.linden.dragon.tavern.common.ActionResult<R>): com.github.arhor.linden.dragon.tavern.common.ActionResult<R> {
        return when (this) {
            is com.github.arhor.linden.dragon.tavern.common.ActionResult.Success<T> -> f(value)
            is com.github.arhor.linden.dragon.tavern.common.ActionResult.Failure -> this
        }
    }

    inline fun catch(f: (Throwable) -> Unit) {
        if (this is com.github.arhor.linden.dragon.tavern.common.ActionResult.Failure) {
            f(error)
        }
    }

    companion object {
        @JvmStatic
        fun success(): com.github.arhor.linden.dragon.tavern.common.ActionResult.Success<Nothing> =
            com.github.arhor.linden.dragon.tavern.common.ActionResult.Success()

        @JvmStatic
        fun <T> success(value: T?): com.github.arhor.linden.dragon.tavern.common.ActionResult.Success<T> =
            com.github.arhor.linden.dragon.tavern.common.ActionResult.Success(value)

        @JvmStatic
        fun failure(error: Throwable): com.github.arhor.linden.dragon.tavern.common.ActionResult.Failure =
            com.github.arhor.linden.dragon.tavern.common.ActionResult.Failure(error)

        @JvmStatic
        inline fun <T> executeCatching(action: () -> T?): com.github.arhor.linden.dragon.tavern.common.ActionResult<T> {
            return try {
                com.github.arhor.linden.dragon.tavern.common.ActionResult.Success(action())
            } catch (error: Throwable) {
                com.github.arhor.linden.dragon.tavern.common.ActionResult.Failure(error)
            }
        }
    }
}
