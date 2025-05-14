package com.epam.learn.gen_ai.service;

import com.epam.learn.gen_ai.exception.ResourceNotFoundException;
import com.epam.learn.gen_ai.model.TodoItem;
import com.epam.learn.gen_ai.repository.TodoRepository;
import com.epam.learn.gen_ai.service.impl.TodoServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    public TodoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    private TodoItem todoItem;

    @BeforeEach
    void setUp() {
        todoItem = new TodoItem();
        todoItem.setId(1L);
        todoItem.setTitle("Test Title");
    }

    /**
     * Test for
     */
    @Test
    public void givenTodo_whenCreateTodo_thenReturnSavedTodo() {
        // given - setup
        BDDMockito.given(todoRepository.save(any())).willReturn(todoItem);

        // when - action
        TodoItem savedTodoItem = todoService.createTodoItem(todoItem);

        // then - result
        Assertions.assertThat(savedTodoItem.getId()).isEqualTo(todoItem.getId());
        Assertions.assertThat(savedTodoItem.getTitle()).isEqualTo(todoItem.getTitle());
    }

    /**
     * Test for
     */
    @Test
    public void givenListTodoItems_whenGetAll_thenReturnListOfTodos() {
        // given - setup
        TodoItem todoItem2 = new TodoItem();
        todoItem.setId(2L);
        todoItem.setTitle("Test Title");

        TodoItem todoItem3 = new TodoItem();
        todoItem.setId(3L);
        todoItem.setTitle("Test Title");

        List<TodoItem> result = List.of(todoItem, todoItem2, todoItem3);

        BDDMockito.given(todoRepository.findAll()).willReturn(result);

        // when - action
        List<TodoItem> allTodoItems = todoService.getAllTodoItems();


        // then - result
        assertEquals(allTodoItems.size(), result.size());
    }

    /**
     * Test for
     */
    @Test
    public void givenTodoItemId_whenDelete_thenDoNothing() {
        // given - setup
        BDDMockito.given(todoRepository.findById(todoItem.getId())).willReturn(Optional.of(todoItem));
        willDoNothing().given(todoRepository).delete(any());

        // when - action
        todoService.deleteTodoItem(todoItem.getId());

        // then - result
        verify(todoRepository, times(1)).delete(todoItem);
    }

    @Test
    public void testGetTodoItemById_Success() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todoItem));

        TodoItem result = todoService.getTodoItemById(1L);
        assertEquals("Test Title", result.getTitle());
    }

    @Test
    public void testGetTodoItemById_NotFound() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> todoService.getTodoItemById(1L));
    }
}
