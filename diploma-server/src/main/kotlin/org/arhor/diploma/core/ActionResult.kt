package org.arhor.diploma.core

@Suppress("unused")
sealed class ActionResult<out T> {

    abstract val isSuccess: Boolean
    abstract val isFailure: Boolean

    data class Success<out T>(val value: T? = null) : ActionResult<T>() {
        override val isSuccess: Boolean
            get() = true
        override val isFailure: Boolean
            get() = false
    }

    data class Failure(val error: Throwable) : ActionResult<Nothing>() {
        override val isSuccess: Boolean
            get() = false
        override val isFailure: Boolean
            get() = true
    }

    inline fun <R> map(f: (T?) -> R): ActionResult<R> {
        return when (this) {
            is Success<T> -> try {
                Success(f(value))
            } catch (error: Throwable) {
                Failure(error)
            }
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
    }
}
