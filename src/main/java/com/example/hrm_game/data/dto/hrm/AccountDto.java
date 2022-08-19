package com.example.hrm_game.data.dto.hrm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto {
    private Long id;
    private String name;
    @JsonProperty("active_admin")
    private List<String> activeAdmin;
    public AccountDto() {
    }
}
