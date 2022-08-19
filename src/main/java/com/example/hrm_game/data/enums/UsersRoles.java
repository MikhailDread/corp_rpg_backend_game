package com.example.hrm_game.data.enums;

import lombok.Getter;

@Getter
public enum UsersRoles {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    MODERATOR("ROLE_MODERATOR");

    private String name;

    UsersRoles(String name) {
        this.name = name;
    }
}
