package com.acme.todolist.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class TodoItemTest {

    @Test
    void finalContent_should_contain_LATE_when_item_is_older_than_24h() {
        // Arrange
        Instant olderThan24h = Instant.now().minus(2, ChronoUnit.DAYS);
        TodoItem item = new TodoItem("1", olderThan24h, "Faire les courses");

        // Act
        String content = item.finalContent();

        // Assert
        assertThat(content).isEqualTo("[LATE!] Faire les courses");
    }

    @Test
    void finalContent_should_not_contain_LATE_when_item_is_recent() {
        // Arrange
        Instant recent = Instant.now().minus(12, ChronoUnit.HOURS);
        TodoItem item = new TodoItem("2", recent, "Laver la voiture");

        // Act
        String content = item.finalContent();

        // Assert
        assertThat(content).isEqualTo("Laver la voiture");
    }
}
