package org.arhor.diploma.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.arhor.diploma.util.JWT
import org.arhor.diploma.util.createLogger
import org.arhor.diploma.web.security.JWTProcessor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class JWTConfig {

    @Bean
    fun jwtProcessor(
        @Value("\${security.jwt.secret}") secret: String?,
        @Value("\${security.jwt.expire}") expire: Int?,
        objectMapper: ObjectMapper
    ): JWTProcessor {
        return JWTProcessor(
            secret = secret ?: generateRandomKey(),
            expire = expire ?: JWT.EXPIRE_SECONDS,
            objectMapper = objectMapper
        )
    }

    private fun generateRandomKey(): String {
        val key = UUID.randomUUID().toString()
        log.debug("Using randomly generated signing key for JWT: ${key}.")
        return key
    }

    companion object {
        private val log = createLogger<JWTConfig>()
    }
}