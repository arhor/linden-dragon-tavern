package org.arhor.diploma.web.security;

interface TokenProvider<A> {

  fun generate(auth: A): String

  fun parse(token: String): String

  fun parseUsername(token: String): String?

  fun validate(token: String): Boolean

  fun authHeaderName(): String

  fun authTokenType(): String
}
