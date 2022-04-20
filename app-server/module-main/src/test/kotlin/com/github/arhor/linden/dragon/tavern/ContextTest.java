package com.github.arhor.linden.dragon.tavern;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Tag("unit")
@SpringJUnitConfig(ContextTest.EmptyContextConfig.class)
class ContextTest {

    @Configuration(proxyBeanMethods = false)
    static class EmptyContextConfig {
    }

    @Autowired
    private ApplicationContext context;

    @Test
    void application_context_should_be_loaded_successfully() {
        assertThat(context)
            .isNotNull();
    }
}
