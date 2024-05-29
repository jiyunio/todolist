package com.jiyunio.todolist.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "category 생성 & 조회 & 수정")
public class CategoryDTO {
    @NotBlank
    @Schema(description = "category 내용", example = "약속")
    private String content;

    @NotBlank
    @Schema(description = "category 색깔 (# 제외)", example = "FFFFFF")
    private String color;

    @Builder
    protected CategoryDTO(String content, String color) {
        this.content = content;
        this.color = color;
    }

}
