package com.jiyunio.todolist.domain.todo.dto;

import com.jiyunio.todolist.domain.todo.domain.Todo;
import com.jiyunio.todolist.domain.todo.domain.TodoList;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoListRes {
    private final Long todoListId;

    private final LocalDate todoListDate;

    private final List<TodoRes> todoRes;

    public static TodoListRes from(TodoList todoList, List<Todo> todos) {
        return TodoListRes.builder()
                .todoListId(todoList.getId())
                .todoListDate(todoList.getTodoListDate())
                .todoRes(todos.stream().map(TodoRes::from).toList())
                .build();
    }
}
