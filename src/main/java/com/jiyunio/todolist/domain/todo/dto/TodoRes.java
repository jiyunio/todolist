package com.jiyunio.todolist.domain.todo.dto;

import com.jiyunio.todolist.domain.category.dto.CategoryRes;
import com.jiyunio.todolist.domain.todo.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoRes {
    @NotNull
    @Schema(description = "todo의 id", example = "1")
    private Long todoId;

    @NotBlank
    @Schema(description = "todo 내용", example = "친구랑 치킨집")
    private String content;

    @Schema(description = "todo check (first default = false)", example = "false")
    private boolean isChecked;

    @NotNull
    @Schema(description = "category")
    private CategoryRes category;

    public static TodoRes from(Todo todo) {
        return TodoRes.builder()
                .todoId(todo.getId())
                .content(todo.getContent())
                .isChecked(todo.getChecked())
                .category(CategoryRes.builder()
                        .categoryId(todo.getCategoryId())
                        .content(todo.getCategoryContent())
                        .color(todo.getCategoryColor())
                        .build())
                .build();
    }
}
