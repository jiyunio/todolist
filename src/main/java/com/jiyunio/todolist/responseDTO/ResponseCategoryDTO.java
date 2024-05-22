package com.jiyunio.todolist.responseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCategoryDTO {
    private Long categoryId;
    private String content;
    private String color;

    @Builder
    protected ResponseCategoryDTO(Long categoryId, String content, String color){
        this.categoryId = categoryId;
        this.content = content;
        this.color = color;
    }
}
