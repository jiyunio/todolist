package com.jiyunio.todolist.domain.todo;

import com.jiyunio.todolist.domain.todo.domain.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> findAllByUserId(String userId);

    Optional<TodoList> findByUserIdAndTodoListDate(String userId, LocalDate localDate);

    @Query("SELECT tl FROM TodoList tl JOIN tl.todos t WHERE t.categoryId = :categoryId and tl.userId = :userId")
    List<TodoList> findAllByUserIdANDCategoryId(String userId, Long categoryId);

    void deleteAllByUserId(String userId);
}
