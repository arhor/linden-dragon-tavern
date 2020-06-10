package org.arhor.diploma.web.model

data class JwtResponse(
    val accessToken: String,
    val tokenType: String
)
