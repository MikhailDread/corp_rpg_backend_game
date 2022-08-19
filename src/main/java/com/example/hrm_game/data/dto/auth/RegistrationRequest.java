package com.example.hrm_game.data.dto.auth;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String login;
    private String password;
}
