package com.github.arhor.linden.dragon.tavern.service.impl;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.Instant;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.arhor.simple.todo.data.repository.ToDoItemRepository;
import dev.arhor.simple.todo.service.StringSanitizer;
import dev.arhor.simple.todo.service.TimeService;
import dev.arhor.simple.todo.service.ToDoItemConverter;
import dev.arhor.simple.todo.service.ToDoItemService;
import dev.arhor.simple.todo.service.impl.ToDoItemServiceImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ToDoItemServiceImpl.class})
public class ToDoItemServiceTest {

    @MockBean private ToDoItemRepository toDoItemRepository;
    @MockBean private ToDoItemConverter toDoItemConverter;
    @MockBean private TimeService timeService;
    @MockBean private StringSanitizer sanitizer;

    @Autowired
    private ToDoItemService toDoItemService;

    @Test
    void deleteOverdueToDoItems_executeWithoutExceptions() {
        // given
        var dateWeekAgo = Instant.now().minus(Duration.ofDays(7));
        when(timeService.weekAgo()).thenReturn(dateWeekAgo);

        // when
        ThrowableAssert.ThrowingCallable action = toDoItemService::deleteOverdueToDoItems;

        // then
        assertThatNoException().isThrownBy(action);

        verify(timeService).weekAgo();
        verify(toDoItemRepository).deleteToDoItemsByDueDateBefore(dateWeekAgo);
    }
}
