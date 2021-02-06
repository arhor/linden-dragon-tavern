package org.arhor.diploma.web.security

import org.springframework.security.core.userdetails.UserDetails

interface TokenProvider {

    fun generate(principal: UserDetails): String

    fun parse(token: String): String

    fun parseUsername(token: String): String?

    fun validate(token: String): Boolean

    fun authHeaderName(): String

    fun authTokenType(): String
}
