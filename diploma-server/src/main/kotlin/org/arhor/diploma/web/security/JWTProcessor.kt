package org.arhor.diploma.web.security

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import java.lang.invoke.MethodHandles
import java.util.*

class JWTProcessor(
    private val secret: String,
    private val expire: Int,
    private val objectMapper: ObjectMapper
) : TokenProcessor<Authentication, JwtAuthToken> {

    private val jwtParser: JwtParser by lazy(LazyThreadSafetyMode.NONE) { Jwts.parser().setSigningKey(secret) }

    override fun generate(source: Authentication): JwtAuthToken {
        log.debug("Generating JwtAuthToken from user authentication: {}", source)


        TODO("Not yet implemented")
    }

    override fun parse(token: String): JwtAuthToken? {
        log.debug("Parsing Json Web Token: {}", token)

        val clearToken = token.removePrefix(authHeaderName).trim()
        val signedJwt: Jws<Claims>? = parseHandlingExceptions(clearToken)

        if (signedJwt !== null && validate(signedJwt)) {
            val payload = objectMapper.readTree(signedJwt.body.subject)

            val ownerIdNode: JsonNode? = payload[FIELD_OWNER_ID]
            val authoritiesNode: JsonNode? = payload[FIELD_AUTHORITIES]

            if (ownerIdNode !== null && authoritiesNode !== null) {
                val ownerId = ownerIdNode.longValue()
                val authorities = authoritiesNode.map { it.asText() }

                val authToken = JwtAuthToken(clearToken, ownerId, authorities)

                log.debug("Token parsed successfully: {}", authToken)

                return authToken
            }
        }
        log.debug("Token parse failed")
        return null
    }

    private fun validate(signedJwt: Jws<Claims>): Boolean {
        val expirationDate = signedJwt.body.expiration
        val currentDate = Date()

        return expirationDate.before(currentDate)
    }

    private fun parseHandlingExceptions(token: String): Jws<Claims>? {
        var result: Jws<Claims>? = null
        try {
            result = jwtParser.parseClaimsJws(token)
        } catch (e: SignatureException) {
            log.error("Invalid JWT signature -> Message: {} ", e.message)
        } catch (e: MalformedJwtException) {
            log.error("Invalid JWT token -> Message: {}", e.message)
        } catch (e: ExpiredJwtException) {
            log.error("Expired JWT token -> Message: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            log.error("Unsupported JWT token -> Message: {}", e.message)
        } catch (e: IllegalArgumentException) {
            log.error("JWT claims string is empty -> Message: {}", e.message)
        }
        return result
    }

    override val authHeaderName: String
        get() = AUTH_HEADER

    override val authTokenType: String
        get() = AUTH_TYPE

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

        private const val AUTH_HEADER = "Authentication"
        private const val AUTH_TYPE = "Bearer"

        private const val FIELD_OWNER_ID = "ownerId"
        private const val FIELD_AUTHORITIES = "authorities"
    }
}