package org.arhor.diploma.commons

@Suppress("unused")
sealed class ActionResult<out T> {

    abstract val isSuccess: Boolean
    abstract val isFailure: Boolean

    data class Success<out T>(val value: T? = null) : ActionResult<T>() {
        override val isSuccess = true
        override val isFailure = false
    }

    data class Failure(val error: Throwable) : ActionResult<Nothing>() {
        override val isSuccess = false
        override val isFailure = true
    }

    override fun toString(): String {
        return when (this) {
            is Success -> value?.toString()
                ?: "success"
            is Failure -> error.message
                ?: "failure"
        }
    }

    inline fun <R> map(f: (T?) -> R?): ActionResult<R> {
        return when (this) {
            is Success<T> -> executeCatching { f(value) }
            is Failure -> this
        }
    }

    inline fun <R> flatMap(f: (T?) -> ActionResult<R>): ActionResult<R> {
        return when (this) {
            is Success<T> -> f(value)
            is Failure -> this
        }
    }

    inline fun catch(f: (Throwable) -> Unit) {
        if (this is Failure) {
            f(error)
        }
    }

    companion object {
        @JvmStatic
        fun success(): Success<Nothing> = Success()

        @JvmStatic
        fun <T> success(value: T?): Success<T> = Success(value)

        @JvmStatic
        fun failure(error: Throwable): Failure = Failure(error)

        @JvmStatic
        inline fun <T> executeCatching(action: () -> T?): ActionResult<T> {
            return try {
                Success(action())
            } catch (error: Throwable) {
                Failure(error)
            }
        }
    }
}
