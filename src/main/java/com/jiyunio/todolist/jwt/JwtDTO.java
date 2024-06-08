package com.jiyunio.todolist.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private String accessToken;
    protected JwtDTO(String accessToken){
        this.accessToken = accessToken;
    }
}
