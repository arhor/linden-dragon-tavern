package org.arhor.diploma.web.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import mu.KotlinLogging
import org.arhor.diploma.JwtStruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.security.Key
import java.util.*

private const val DEFAULT_EXPIRE = 600

private val logger = KotlinLogging.logger {}

class JwtProvider(private val objectMapper: ObjectMapper) : TokenProvider {

    @Value("\${security.jwt.expire}")
    var expire: Int = DEFAULT_EXPIRE

    override val authHeaderName: String
        get() = "Authentication"

    override val authTokenType: String
        get() = "Bearer"

    private val secret: Key by lazy {
        Keys.secretKeyFor(SignatureAlgorithm.HS512)
    }

    private val jwtParser: JwtParser by lazy {
        Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
    }

    override fun generate(principal: UserDetails): String {
        val roles = principal.authorities.map(GrantedAuthority::getAuthority)

        val dateNow = Date()
        val dateExp = Date(dateNow.time + expire)

        return Jwts.builder()
            .claim(JwtStruct.USERNAME, principal.username)
            .claim(JwtStruct.ROLES, roles)
            .setIssuedAt(dateNow)
            .setExpiration(dateExp)
            .signWith(secret)
            .compact()
    }

    override fun parse(token: String): String {
        return token.replace(authTokenType, "").trim()
    }

    override fun parseUsername(token: String): String? {
        return jwtParser.parseClaimsJws(token)
            ?.body
            ?.subject
            ?.let {
                objectMapper
                    .readTree(it)
                    .findValue(JwtStruct.USERNAME)?.asText()
            }
    }

    override fun validate(token: String): Boolean = try {
        jwtParser.parseClaimsJws(token)
        true
    } catch (ex: JwtException) {
        logger.error(ex.message, ex)
        false
    }

    override fun extractUsernameAndRoles(authHeader: String): Pair<String, Collection<String>> {
        val token = parse(authHeader)

        val subject = jwtParser.parseClaimsJws(token).body.subject
        val objectTree = objectMapper.readTree(subject)

        val username = objectTree.findValue(JwtStruct.USERNAME).asText()
        val roles = objectTree.findValue(JwtStruct.ROLES).map { role -> role.asText() }

        return username to roles
    }
}
