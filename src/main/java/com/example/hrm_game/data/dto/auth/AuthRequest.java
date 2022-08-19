package com.example.hrm_game.data.dto.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
