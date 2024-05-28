package com.jiyunio.todolist.responseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
public class ResponseTodoDTO {
    private Long todoId;
    private String content;
    private boolean checked;
    private String category;
    private LocalDate writeDate;
    private LocalDate setDate;
    private String color;

    @Builder
    protected ResponseTodoDTO(Long todoId, String content, boolean checked,
                              String category, LocalDate writeDate, LocalDate setDate, String color){
        this.todoId = todoId;
        this.content = content;
        this.checked = checked;
        this.category = category;
        this.writeDate = writeDate;
        this.setDate = setDate;
        this.color = color;
    }
}
