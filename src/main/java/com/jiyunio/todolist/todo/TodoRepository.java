package com.jiyunio.todolist.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserId(String userId);

    Optional<Todo> findById(Long todoId);

    List<Todo> findByCategoryId(Long categoryId);
}
