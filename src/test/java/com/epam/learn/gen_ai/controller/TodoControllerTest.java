package com.epam.learn.gen_ai.controller;


import com.epam.learn.gen_ai.model.TodoItem;
import com.epam.learn.gen_ai.service.impl.TodoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoServiceImpl todoService;

    @Autowired
    private ObjectMapper objectMapper;

    private TodoItem mockItem;

    @BeforeEach
    void setup() {
        mockItem = new TodoItem();
        mockItem.setId(1L);
        mockItem.setTitle("Test Todo");
        mockItem.setDescription("This is a test");
    }

    @Test
    void testCreateTodoItem() throws Exception {
        Mockito.when(todoService.createTodoItem(any(TodoItem.class))).thenReturn(mockItem);

        mockMvc.perform(post("/v1/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Todo"));
    }

    @Test
    void testGetTodoItemById() throws Exception {
        Mockito.when(todoService.getTodoItemById(1L)).thenReturn(mockItem);

        mockMvc.perform(get("/v1/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetAllTodoItems() throws Exception {
        Mockito.when(todoService.getAllTodoItems()).thenReturn(Collections.singletonList(mockItem));

        mockMvc.perform(get("/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Todo"));
    }

    @Test
    void testUpdateTodoItem() throws Exception {
        mockItem.setTitle("Updated Title");
        Mockito.when(todoService.updateTodoItem(eq(1L), any(TodoItem.class))).thenReturn(mockItem);

        mockMvc.perform(put("/v1/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void testDeleteTodoItem() throws Exception {
        Mockito.doNothing().when(todoService).deleteTodoItem(1L);

        mockMvc.perform(delete("/v1/todos/1"))
                .andExpect(status().isNoContent());
    }
}
