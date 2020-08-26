package org.arhor.diploma.service;

import org.arhor.diploma.data.file.MonsterProvider;
import org.arhor.diploma.data.file.MonsterProviderImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class MonsterReaderTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        MonsterProvider monsterService(ResourceLoader loader) {
            return new MonsterProviderImpl(loader);
        }
    }

    @Autowired
    private MonsterProvider service;

    @Test
    void fetchAllMonsters() {
        // when
        var monsters = service.getList();

        // then
        assertThat(monsters)
                .isNotNull()
                .isNotEmpty();
    }
}
