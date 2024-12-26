package com.jiyunio.todolist.global.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    @NotBlank
    @Schema(description = "전달 메시지", example = "삭제 성공")
    String msg;

    @Builder
    ResponseDTO(String msg) {
        this.msg = msg;
    }
}
