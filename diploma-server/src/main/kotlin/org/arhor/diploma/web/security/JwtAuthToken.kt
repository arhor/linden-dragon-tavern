package org.arhor.diploma.web.security

data class JwtAuthToken(
    override val token: String,
    override val ownerId: Long,
    override val authorities: List<String>
): AuthToken