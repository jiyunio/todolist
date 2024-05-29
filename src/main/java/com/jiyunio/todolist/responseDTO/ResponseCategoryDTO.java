package com.jiyunio.todolist.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCategoryDTO {
    @NotNull
    @Schema(description = "category Id", example = "1")
    private Long categoryId;

    @NotBlank
    @Schema(description = "category 내용", example = "약속")
    private String content;

    @NotBlank
    @Schema(description = "category 색깔 (# 제외)", example = "FFFFFF")
    private String color;

    @Builder
    protected ResponseCategoryDTO(Long categoryId, String content, String color){
        this.categoryId = categoryId;
        this.content = content;
        this.color = color;
    }
}
