package com.jiyunio.todolist.customError;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class ErrorDTO {
    @Schema(description = "ErrorCode", example = "400_Bad_Request")
    String code;
    @Schema(description = "에러 메시지", example = "아이디 및 비밀번호가 맞지 않습니다.")
    String msg;

    public static ResponseEntity<ErrorDTO> toResponseEntity(CustomException e) {
        ErrorCode error = e.getErrorCode();
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorDTO.builder()
                        .code(error.getCode())
                        .msg(error.getMessage())
                        .build());
    }
}
