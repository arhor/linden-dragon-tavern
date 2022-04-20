package com.github.arhor.linden.dragon.tavern.web.security

import org.springframework.security.core.userdetails.UserDetails

interface TokenProvider {

    val authHeaderName: String
    val authTokenType: String

    fun generate(principal: UserDetails): String

    fun parse(token: String): String

    fun parseUsername(token: String): String?

    fun validate(token: String): Boolean

    fun extractUsernameAndRoles(authHeader: String): Pair<String, Collection<String>>
}
