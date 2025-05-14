package com.epam.learn.gen_ai.service;

import com.epam.learn.gen_ai.model.TodoItem;

import java.util.List;

public interface TodoService {
    TodoItem createTodoItem(TodoItem todoItem);
    TodoItem getTodoItemById(Long id);
    List<TodoItem> getAllTodoItems();
    TodoItem updateTodoItem(Long id, TodoItem todoItem);
    void deleteTodoItem(Long id);
}
