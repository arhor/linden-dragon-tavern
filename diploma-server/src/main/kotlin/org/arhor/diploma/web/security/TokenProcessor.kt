package org.arhor.diploma.web.security

interface TokenProcessor<S, T : AuthToken> {

  fun generate(source: S): T

  fun parse(token: String): T?

  val authHeaderName: String

  val authTokenType: String

}