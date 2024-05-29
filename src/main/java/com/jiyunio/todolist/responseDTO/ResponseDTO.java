package com.jiyunio.todolist.responseDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    @NotBlank
    String msg;

    @Builder
    ResponseDTO(String msg) {
        this.msg = msg;
    }
}
