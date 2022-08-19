package com.example.hrm_game.data.dto.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDto {
    private Long id;
    private ItemEnum itemEnum;

    public ItemDto() {
    }
}
