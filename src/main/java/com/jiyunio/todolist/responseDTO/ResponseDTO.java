package com.jiyunio.todolist.responseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    String msg;

    @Builder
    ResponseDTO(String msg) {
        this.msg = msg;
    }
}
