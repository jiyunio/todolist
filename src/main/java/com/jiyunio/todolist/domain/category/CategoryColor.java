package com.jiyunio.todolist.domain.category;

import lombok.Getter;

@Getter
public enum CategoryColor {
    BLUE("CAD3FF"),
    ORENGE("FFD6B1"),
    GREEN("AFFFA2"),
    PINCK("FFC9ED"),
    YELLOW("FFF493");

    private final String hex;
    CategoryColor(String hex){
        this.hex = hex;
    }
}
