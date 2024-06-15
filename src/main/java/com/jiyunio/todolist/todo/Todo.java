package com.jiyunio.todolist.todo;

import com.jiyunio.todolist.category.CategoryDTO;
import com.jiyunio.todolist.todo.dto.UpdateTodoDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todoId")
    private Long id;

    private String userId;

    @Lob // 길이 제한 X
    private String content;

    private Boolean checked;

    private LocalDate setDate;

    private Long categoryId;

    private String categoryColor;

    private String categoryContent;


    @Builder
    protected Todo(String userId, String content, Boolean checked, LocalDate setDate,
                   Long categoryId, String categoryContent, String categoryColor) {
        this.userId = userId;
        this.content = content;
        this.checked = checked;
        this.setDate = setDate;
        this.categoryId = categoryId;
        this.categoryContent = categoryContent;
        this.categoryColor = categoryColor;
    }

    protected void updateTodo(UpdateTodoDTO updateTodoDto) {
        this.content = updateTodoDto.getContent();
        this.checked = updateTodoDto.getChecked();
        this.setDate = updateTodoDto.getSetDate();
    }

    protected void updateCategory(CategoryDTO categoryDTO) {
        this.categoryContent = categoryDTO.getContent();
        this.categoryColor = categoryDTO.getColor();
    }
}