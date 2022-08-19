package com.example.hrm_game.data.dto.game;

import lombok.Getter;

@Getter
public enum ItemEnum {
    CHEST(1, "CHEST"),
    DEFENDER(2, "DEFENDER"),
    HELMET(3, "HELMET"),
    LEGGINGS(4, "LEGGINGS"),
    WEAPON(5, "WEAPON");
    private final int code;
    private final String type;

    ItemEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }
}
