package org.arhor.diploma.web.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import io.jsonwebtoken.*
import org.arhor.diploma.JwtStruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.lang.invoke.MethodHandles
import java.util.*

@Component
class JwtProvider(
    private val objectMapper: ObjectMapper
) : TokenProvider {

    @Value("\${security.jwt.secret}")
    var secret: String? = null

    @Value("\${security.jwt.expire}")
    var expire: Int? = null

    override val authHeaderName: String
        get() = "Authentication"

    override val authTokenType: String
        get() = "Bearer"

    private val jwtParser: JwtParser by lazy(LazyThreadSafetyMode.NONE) {
        Jwts.parser().setSigningKey(secret ?: generateRandomKey())
    }

    private fun generateRandomKey(): String {
        return UUID.randomUUID()
            .toString()
            .also { log.debug("Using randomly generated signing key for JWT: ${it}.") }
    }

    override fun generate(principal: UserDetails): String {
        val roles =
            principal.authorities//.asSequence()
                .map(GrantedAuthority::getAuthority)
//                .fold(objectMapper.createArrayNode()) { result, authority ->
//                    result.add(authority)
//                }

        val dateNow = Date()
        val dateExp = Date(dateNow.time + (expire ?: DEFAULT_EXPIRE))

        return Jwts.builder()
            .claim(JwtStruct.USERNAME, principal.username)
            .claim(JwtStruct.ROLES, roles)
            .setIssuedAt(dateNow)
            .setExpiration(dateExp)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    override fun parse(token: String): String {
        return token.replace(authTokenType, "").trim()
    }

    override fun parseUsername(token: String): String? {
        return jwtParser.parseClaimsJws(token)?.body?.subject?.let {
            objectMapper
                .readTree(it)
                .findValue(JwtStruct.USERNAME)?.asText()
        }
    }

    override fun validate(token: String): Boolean {
        try {
            return jwtParser.parseClaimsJws(token).body.expiration.after(Date())
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

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
        const val DEFAULT_EXPIRE = 600
    }
}
