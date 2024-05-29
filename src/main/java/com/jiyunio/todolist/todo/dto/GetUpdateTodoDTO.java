package com.jiyunio.todolist.todo.dto;

import com.jiyunio.todolist.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "todo 수정")
public class GetUpdateTodoDTO {
    @NotBlank(message = "todo를 작성해주세요.")
    private String content;

    @NotNull
    private Boolean checked;

    @NotNull(message = "작성 일자를 선택해주세요.")
    private LocalDate writeDate;

    @NotNull(message = "설정 일자를 선택해주세요.")
    private LocalDate setDate;

    @NotBlank
    private Category category;

    @Builder
    protected GetUpdateTodoDTO(String content, Boolean checked, Category category,
                               LocalDate writeDate, LocalDate setDate, String color) {
        this.content = content;
        this.checked = checked;
        this.category = category;
        this.writeDate = writeDate;
        this.setDate = setDate;
    }
}
