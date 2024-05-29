package com.jiyunio.todolist.todo;

import com.jiyunio.todolist.category.Category;
import com.jiyunio.todolist.category.CategoryDTO;
import com.jiyunio.todolist.member.Member;
import com.jiyunio.todolist.todo.dto.GetUpdateTodoDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todoId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Lob // 길이 제한 X
    private String content;

    private Boolean checked;

    private LocalDate writeDate;

    private LocalDate setDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;


    @Builder
    protected Todo(Member member, String content, Boolean checked,
                   LocalDate writeDate, LocalDate setDate, Category category) {
        this.member = member;
        this.content = content;
        this.checked = checked;
        this.writeDate = writeDate;
        this.setDate = setDate;
        this.category = category;
    }

    protected void updateTodo(GetUpdateTodoDTO getUpdateTodoDto) {
        this.content = getUpdateTodoDto.getContent();
        this.checked = getUpdateTodoDto.getChecked();
        this.writeDate = getUpdateTodoDto.getWriteDate();
        this.setDate = getUpdateTodoDto.getSetDate();
    }
}