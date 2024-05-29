package com.jiyunio.todolist.todo;

import com.jiyunio.todolist.category.CategoryDTO;
import com.jiyunio.todolist.member.Member;
import com.jiyunio.todolist.todo.dto.GetUpdateTodoDTO;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Lob // 길이 제한 X
    private String content;

    private Boolean checked;

    private LocalDate writeDate;

    private LocalDate setDate;

    private Long categoryId;

    private String categoryColor;

    private String categoryContent;


    @Builder
    protected Todo(Member member, String content, Boolean checked, LocalDate writeDate, LocalDate setDate,
                   Long categoryId, String categoryContent, String categoryColor) {
        this.member = member;
        this.content = content;
        this.checked = checked;
        this.writeDate = writeDate;
        this.setDate = setDate;
        this.categoryId = categoryId;
        this.categoryContent = categoryContent;
        this.categoryColor = categoryColor;
    }

    protected void updateTodo(GetUpdateTodoDTO getUpdateTodoDto) {
        this.content = getUpdateTodoDto.getContent();
        this.checked = getUpdateTodoDto.getChecked();
        this.writeDate = getUpdateTodoDto.getWriteDate();
        this.setDate = getUpdateTodoDto.getSetDate();
    }

    protected void updateCategory(CategoryDTO categoryDTO) {
        this.categoryContent = categoryDTO.getContent();
        this.categoryColor = categoryDTO.getColor();
    }
}