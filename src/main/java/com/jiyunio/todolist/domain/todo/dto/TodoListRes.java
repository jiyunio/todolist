package com.jiyunio.todolist.domain.todo.dto;

import com.jiyunio.todolist.domain.todo.domain.TodoList;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoListRes {
    private Long todoListId;

    private LocalDate todoListDate;

    private boolean isToday;

    private List<TodoRes> todoRes;

    public static TodoListRes from(TodoList todoList) {
        return TodoListRes.builder()
                .todoListId(todoList.getId())
                .todoListDate(todoList.getTodoListDate())
                .isToday(todoList.isToday())
                .todoRes(todoList.getTodos().stream().map(TodoRes::from).toList())
                .build();
    }
}
