package com.jiyunio.todolist.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "카테고리 생성 & 조회 & 수정")
public class CategoryDTO {
    @NotBlank
    private String content;

    @NotBlank
    private String color;

    @Builder
    protected CategoryDTO(String content, String color) {
        this.content = content;
        this.color = color;
    }

}
