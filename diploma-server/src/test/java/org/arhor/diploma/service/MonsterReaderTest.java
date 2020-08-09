package org.arhor.diploma.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig
class MonsterReaderTest {

    @TestConfiguration
    @ComponentScan("org.arhor.diploma.service")
    static class TestConfig {}

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MonsterService service;

    @Test
    void fetchAllMonsters() {
        assertDoesNotThrow(() -> {
            // when
            var monsters = service.getList();

            // then
            assertNotNull(monsters);

            monsters.forEach(monster -> {
                try {
                    if (monster.getLegendaryActions() != null)
                    System.out.println(mapper.writeValueAsString(monster));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
