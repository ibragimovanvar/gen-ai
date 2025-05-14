package com.epam.learn.gen_ai.repository;

import com.epam.learn.gen_ai.model.TodoItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();
    }

    @Test
    @DisplayName("Should save and retrieve a todo item by ID")
    void testSaveAndFindById() {
        TodoItem item = new TodoItem();
        item.setTitle("Repository Test");
        item.setDescription("Testing repository layer");
        TodoItem saved = todoRepository.save(item);

        Optional<TodoItem> found = todoRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Repository Test");
    }

    @Test
    @DisplayName("Should delete a todo item")
    void testDelete() {
        TodoItem item = new TodoItem();
        item.setTitle("Delete Test");
        item.setDescription("To be deleted");

        TodoItem saved = todoRepository.save(item);
        Long id = saved.getId();

        todoRepository.deleteById(id);

        Optional<TodoItem> deleted = todoRepository.findById(id);
        assertThat(deleted).isNotPresent();
    }

    @Test
    @DisplayName("Should retrieve all todo items")
    void testFindAll() {
        todoRepository.save(new TodoItem(null, "Item 1", "Desc 1"));
        todoRepository.save(new TodoItem(null, "Item 2", "Desc 2"));

        var list = todoRepository.findAll();
        assertThat(list).hasSizeGreaterThanOrEqualTo(2);
    }
}
