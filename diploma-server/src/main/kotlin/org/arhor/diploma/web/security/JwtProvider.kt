package org.arhor.diploma.web.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import io.jsonwebtoken.*
import org.arhor.diploma.util.toArrayNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider(
    private val objectMapper: ObjectMapper
) : TokenProvider<Authentication> {

    private val jwtParser: JwtParser by lazy(LazyThreadSafetyMode.NONE) {
        Jwts.parser().setSigningKey(secret ?: generateRandomKey())
    }

    @Value("\${security.jwt.secret}")
    var secret: String? = null

    @Value("\${security.jwt.expire}")
    var expire: Int? = null

    private fun generateRandomKey(): String {
        val key = UUID.randomUUID().toString()
        log.debug("Using randomly generated signing key for JWT: ${key}.")
        return key
    }

    override fun generate(auth: Authentication): String {
        return Jwts.builder()
            .setSubject(auth.generateSubject())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + (expire ?: DEFAULT_EXPIRE)))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    private fun Authentication.generateSubject(): String {
        return asJsonString { principal }
    }

    override fun parse(token: String): String {
        return token.replace(authTokenType(), "").trim()
    }

    override fun parseUsername(token: String): String? {
        return jwtParser.parseClaimsJws(token)?.body?.subject?.let {
            objectMapper
                .readTree(it)
                .findValue(FIELD_USERNAME)?.asText()
        }
    }

    override fun validate(token: String): Boolean {
        try {
            return jwtParser.parseClaimsJws(token).body.expiration.before(Date())
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
        return false
    }

    private fun asJsonString(principalSource: () -> Any): String {
        val principal = principalSource() as UserDetails

        val roles =
            principal.authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toArrayNode(objectMapper))

        return objectMapper.createObjectNode()
            .put(FIELD_USERNAME, principal.username)
            .set<ArrayNode>(FIELD_ROLES, roles)
            .toString()
    }

    override fun authHeaderName(): String {
        return "Authentication"
    }

    override fun authTokenType(): String {
        return "Bearer"
    }

    companion object {
        @JvmStatic
        private val log: Logger = LoggerFactory.getLogger(JwtProvider::class.java)

        const val FIELD_USERNAME = "username"
        const val FIELD_ROLES = "roles"
        const val DEFAULT_EXPIRE = 600
    }
}
