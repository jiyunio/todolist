package com.jiyunio.todolist.global.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private String token;

    protected JwtDTO(String token) {
        this.token = token;
    }
}
