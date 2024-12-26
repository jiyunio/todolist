package com.jiyunio.todolist.domain.todo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_list_id")
    private Long id;

    private String userId;

    private LocalDate todoListDate;

    @Builder
    private TodoList(LocalDate todoListDate, String userId) {
        this.todoListDate = todoListDate;
        this.userId = userId;
    }
}
