package com.jiyunio.todolist.domain.todo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private LocalDate todoListDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Todo> todos = new ArrayList<>();

    @Builder
    private TodoList(LocalDate todoListDate, String userId, List<Todo> todos) {
        this.todoListDate = todoListDate;
        this.userId = userId;
        this.todos = todos;
    }
}
