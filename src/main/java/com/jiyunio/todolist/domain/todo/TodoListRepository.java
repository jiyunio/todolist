package com.jiyunio.todolist.domain.todo;

import com.jiyunio.todolist.domain.todo.domain.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> findAllByUserId(String userId);

    Optional<TodoList> findByUserIdAndTodoListDate(String userId, LocalDate localDate);

    @Query("select tl from TodoList tl join Todo t on tl.id = t.todoListId where t.categoryId = :categoryId and tl.userId = :userId")
    List<TodoList> findAllByUserIdAndCategoryId(@Param("userId") String userId, @Param("categoryId") Long categoryId);

    void deleteAllByUserId(String userId);

    @Query("select count(tl) > 0 from TodoList tl where tl.userId = :userId and tl.todoListDate = :todoListDate")
    boolean existsByUserIdAndTodoListDate(@Param("userId") String userId, @Param("todoListDate") LocalDate todoListDate);

}
