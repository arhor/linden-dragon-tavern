package org.arhor.diploma.startup

sealed class VerificationResult(val message: String)

class Success(message: String): VerificationResult(message)

class Failure(message: String): VerificationResult(message)