package com.jiyunio.todolist.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ResponseTodoDTO {
    @NotNull
    @Schema(description = "todo의 id", example = "1")
    private Long todoId;

    @NotBlank
    @Schema(description = "todo 내용", example = "친구랑 치킨집")
    private String content;

    @Schema(description = "todo check (first default = false)", example = "false")
    private boolean checked;

    @NotNull
    @Schema(description = "todo 설정 날짜", example = "YYYY-MM-DD")
    private LocalDate setDate;

    @NotNull
    @Schema(description = "category")
    private ResponseCategoryDTO category;

    @Builder
    protected ResponseTodoDTO(Long todoId, String content, boolean checked, LocalDate setDate, ResponseCategoryDTO category) {
        this.todoId = todoId;
        this.content = content;
        this.checked = checked;
        this.setDate = setDate;
        this.category = category;
    }
}
