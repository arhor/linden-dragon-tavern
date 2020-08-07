package org.arhor.diploma.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.arhor.diploma.service.dto.Monster;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MonsterReaderTest {

    @Autowired
    private Reader<Monster, Integer> reader;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void fetchAllMonsters() {
        assertDoesNotThrow(() -> {
            // when
            var monsters = reader.getList();

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
