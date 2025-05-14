package com.epam.learn.gen_ai.repository;

import com.epam.learn.gen_ai.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoItem, Long> {

}
