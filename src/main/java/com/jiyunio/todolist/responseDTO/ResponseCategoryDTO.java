package com.jiyunio.todolist.responseDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCategoryDTO {
    @NotNull
    private Long categoryId;

    @NotBlank
    private String content;

    @NotBlank
    private String color;

    @Builder
    protected ResponseCategoryDTO(Long categoryId, String content, String color){
        this.categoryId = categoryId;
        this.content = content;
        this.color = color;
    }
}
