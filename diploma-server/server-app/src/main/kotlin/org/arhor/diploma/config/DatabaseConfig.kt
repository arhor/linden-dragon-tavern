package org.arhor.diploma.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EntityScan("org.arhor.diploma.data.persistence.domain")
@EnableJpaRepositories(
    "org.arhor.diploma.data.persistence.repository",
    "org.arhor.diploma.data.persistence.domain.extension"
)
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
class DatabaseConfig