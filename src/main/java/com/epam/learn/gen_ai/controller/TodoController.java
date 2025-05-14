package com.epam.learn.gen_ai.controller;

import com.epam.learn.gen_ai.model.TodoItem;
import com.epam.learn.gen_ai.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoItem> createTodoItem(@RequestBody TodoItem todoItem) {
        return ResponseEntity.ok(todoService.createTodoItem(todoItem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodoItemById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoItemById(id));
    }

    @GetMapping
    public ResponseEntity<List<TodoItem>> getAllTodoItems() {
        return ResponseEntity.ok(todoService.getAllTodoItems());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateTodoItem(@PathVariable Long id, @RequestBody TodoItem todoItem) {
        return ResponseEntity.ok(todoService.updateTodoItem(id, todoItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Long id) {
        todoService.deleteTodoItem(id);
        return ResponseEntity.noContent().build();
    }
}
