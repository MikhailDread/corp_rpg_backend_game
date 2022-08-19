package com.example.hrm_game.data.dto.hrm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectRoleDto {
    private Long id;
    private String name;
    @JsonProperty("is_lead")
    private Boolean isLead;
    private String description;
}
