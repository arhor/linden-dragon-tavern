package com.github.arhor.linden.dragon.tavern

import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@Tag("unit")
@SpringJUnitConfig(ContextTest.EmptyContextConfig::class)
internal class ContextTest {

    @Configuration(proxyBeanMethods = false)
    class EmptyContextConfig

    @Autowired
    private lateinit var ctx: ApplicationContext

    @Test
    fun `application context should be loaded successfully`() {
        assertThat(ctx)
            .isNotNull
    }
}
