package com.acme.todolist.adapters.persistence;

import com.acme.todolist.domain.TodoItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TodoItemPersistenceAdapter.class, TodoItemMapper.class})
class TodoItemPersistenceAdapterTest {

    @Autowired
    private TodoItemPersistenceAdapter persistenceAdapter;

    @Autowired
    private TodoItemRepository repository;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void storeNewTodoItem_should_save_in_database() {
        // Arrange
        TodoItem item = new TodoItem("3", Instant.now(), "Tâche en DB");

        // Act
        persistenceAdapter.storeNewTodoItem(item);

        // Assert
        List<TodoItemJpaEntity> entities = repository.findAll();
        assertThat(entities).hasSize(1);
        assertThat(entities.get(0).getContent()).isEqualTo("Tâche en DB");
    }
}
