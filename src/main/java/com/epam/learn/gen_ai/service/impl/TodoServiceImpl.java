package com.epam.learn.gen_ai.service.impl;


import com.epam.learn.gen_ai.exception.ResourceNotFoundException;
import com.epam.learn.gen_ai.model.TodoItem;
import com.epam.learn.gen_ai.repository.TodoRepository;
import com.epam.learn.gen_ai.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public TodoItem createTodoItem(TodoItem todoItem) {
        return todoRepository.save(todoItem);
    }

    @Override
    public TodoItem getTodoItemById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TodoItem not found with id: " + id));
    }

    @Override
    public List<TodoItem> getAllTodoItems() {
        return todoRepository.findAll();
    }

    @Override
    public TodoItem updateTodoItem(Long id, TodoItem todoItemDetails) {
        TodoItem todoItem = getTodoItemById(id);
        todoItem.setTitle(todoItemDetails.getTitle());
        todoItem.setDescription(todoItemDetails.getDescription());
        return todoRepository.save(todoItem);
    }

    @Override
    public void deleteTodoItem(Long id) {
        TodoItem todoItem = getTodoItemById(id);
        todoRepository.delete(todoItem);
    }
}
