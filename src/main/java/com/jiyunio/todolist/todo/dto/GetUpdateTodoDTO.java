package com.jiyunio.todolist.todo.dto;

import com.jiyunio.todolist.category.Category;
import com.jiyunio.todolist.category.CategoryDTO;
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
    @Schema(description = "todo 내용", example = "친구랑 홍대")
    private String content;

    @NotNull
    @Schema(description = "todo checked", example = "true")
    private Boolean checked;

    @NotNull(message = "작성 일자를 선택해주세요.")
    @Schema(description = "todo 작성 일자", example = "YYYY-MM-DD")
    private LocalDate writeDate;

    @NotNull(message = "설정 일자를 선택해주세요.")
    @Schema(description = "todo 설정 일자", example = "YYYY-MM-DD")
    private LocalDate setDate;

    @NotNull
    private Long categoryId;

    @NotBlank
    private String categoryContent;

    @NotBlank
    private String categoryColor;

    @Builder
    protected GetUpdateTodoDTO(String content, Boolean checked, LocalDate writeDate, LocalDate setDate,
                               Long categoryId, String categoryContent, String categoryColor) {
        this.content = content;
        this.checked = checked;
        this.writeDate = writeDate;
        this.setDate = setDate;
        this.categoryId = categoryId;
        this.categoryContent = categoryContent;
        this.categoryColor = categoryColor;
    }
}
