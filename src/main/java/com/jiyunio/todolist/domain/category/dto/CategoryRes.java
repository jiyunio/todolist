package com.jiyunio.todolist.domain.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryRes {
    @NotNull
    @Schema(description = "category Id", example = "1")
    private Long categoryId;

    @NotBlank
    @Schema(description = "category 내용", example = "약속")
    private String content;

    @NotBlank
    @Schema(description = "category 색깔 (# 제외)", example = "FFFFFF")
    private String color;
}
