package com.jiyunio.todolist.global.customError;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 400 Bad Request
    NOT_SAME_CONFIRM_PASSWORD("001_NOT_SAME_CONFIRM_PASSWORD", "비밀번호 확인이 맞지 않습니다."),
    NO_ANYMORE_CATEGORY("002_NO_ANYMORE_CATEGORY", "카테고리는 1개 이상이어야 합니다."),

    // 401 AUTHORIZATION
    NO_AUTHORIZATION("011_NO_AUTHORIZATION", "접근 권한이 없습니다."),
    WRONG_USERID("013_WRONG_USERID", "아이디가 맞지 않습니다."),
    WRONG_PASSWORD("012_WRONG_PASSWORD", "비밀번호가 맞지 않습니다."),


    // 404 Not Found
    NOT_EXIST_MEMBER("041_NOT_EXIST_MEMBER", "회원이 존재하지 않습니다."),
    NOT_EXIST_TODO("042_NOT_EXIST_TODO", "TODO가 존재하지 않습니다."),
    NOT_EXIST_TODOLIST("043_NOT_EXIST_TODOLIST", "TodoList가 존재하지 않습니다."),
    NOT_EXIST_CATEGORY("044_NOT_EXIST_CATEGORY", "Category가 존재하지 않습니다."),

    // 409 Conflict (중복된 값)
    EXIST_USERID("091_EXIST_USERID", "이미 존재하는 아이디입니다."),
    EXIST_EMAIL("092_EXIST_EMAIL", "이미 존재하는 이메일입니다."),
    EXIST_TODOLIST("093_EXIST_TODOLIST", "이미 존재하는 todo list입니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
