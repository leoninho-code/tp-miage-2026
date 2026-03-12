package com.acme.todolist.adapters.rest_api;

import com.acme.todolist.application.port.in.AddTodoItem;
import com.acme.todolist.application.port.in.GetTodoItems;
import com.acme.todolist.domain.TodoItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TodoListController.class)
class TodoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetTodoItems getTodoItemsQuery;

    @MockBean
    private AddTodoItem addTodoItemUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void ajouterItem_should_return_201_and_call_usecase() throws Exception {
        // Arrange
        TodoItem item = new TodoItem("1", Instant.now(), "Faire les courses");
        String itemJson = objectMapper.writeValueAsString(item);

        // Act & Assert
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(itemJson))
                .andExpect(status().isCreated());

        // Verify that the usecase was called
        verify(addTodoItemUseCase).addTodoItem(any(TodoItem.class));
    }
}
