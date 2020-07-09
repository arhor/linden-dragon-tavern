package org.arhor.diploma.util

sealed class ActionResult<out T> {
  fun isSuccess(): Boolean = this is Success<*>
  fun isFailure(): Boolean = this is Failure

  inline fun <R> map(f: (T?) -> R): ActionResult<R> {
    return when(this) {
      is Success<T> -> Success(f(value))
      is Failure -> this
    }
  }

  inline fun <R> flatMap(f: (T?) -> ActionResult<R>): ActionResult<R> {
    return when(this) {
      is Success<T> -> f(value)
      is Failure -> this
    }
  }

  inline fun catch(f: (Throwable) -> Unit) {
    if (this is Failure) {
      f(error)
    }
  }
}

class Success<out T>(val value: T?) : ActionResult<T>()

class Failure(val error: Throwable): ActionResult<Nothing>()
