package com.acme.todolist.adapters.rest_api;

import com.acme.todolist.application.port.in.AddTodoItem;
import com.acme.todolist.application.port.in.GetTodoItems;
import com.acme.todolist.domain.TodoItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TodoListController.class)
class TodoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetTodoItems getTodoItemsQuery;

    @MockBean
    private AddTodoItem addTodoItemUseCase;

    @Test
    void getAllTodoItems_should_return_list_of_items() throws Exception {
        // Arrange
        TodoItem item = new TodoItem("1", Instant.now(), "Test item");
        given(getTodoItemsQuery.getAllTodoItems()).willReturn(Collections.singletonList(item));

        // Act & Assert
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Test item"));
    }

    @Test
    void ajouterItem_should_create_item_and_return_201() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Nouvelle tâche\"}"))
                .andExpect(status().isCreated());

        verify(addTodoItemUseCase).addTodoItem(any(TodoItem.class));
    }
}
