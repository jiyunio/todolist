package com.jiyunio.todolist.domain.todo;

import com.jiyunio.todolist.domain.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCategoryId(Long categoryId);

    List<Todo> findAllByTodoListId(Long todoListId);
}
