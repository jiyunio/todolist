package com.jiyunio.todolist.domain.todo.domain;

import com.jiyunio.todolist.domain.category.dto.CategoryRes;
import com.jiyunio.todolist.domain.todo.dto.UpdateTodoReq;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Lob // 길이 제한 X
    private String content;

    private Boolean checked;

    private Long categoryId;

    private String categoryColor;

    private String categoryContent;

    private Long todoListId;

    @Builder
    private Todo(String content, Boolean checked,
                 Long categoryId, String categoryContent, String categoryColor, Long todoListId) {
        this.content = content;
        this.checked = checked;
        this.categoryId = categoryId;
        this.categoryContent = categoryContent;
        this.categoryColor = categoryColor;
        this.todoListId = todoListId;
    }

    public void updateTodo(UpdateTodoReq updateTodoReq) {
        this.content = updateTodoReq.getContent();
        this.checked = updateTodoReq.getChecked();
    }

    public void updateCategory(CategoryRes categoryDTO) {
        this.categoryId = categoryDTO.getCategoryId();
        this.categoryContent = categoryDTO.getContent();
        this.categoryColor = categoryDTO.getColor();
    }
}