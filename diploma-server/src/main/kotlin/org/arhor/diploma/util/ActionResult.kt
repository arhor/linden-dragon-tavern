package org.arhor.diploma.util

sealed class ActionResult(val message: String)

class Success(message: String): ActionResult(message)

class Failure(message: String): ActionResult(message)