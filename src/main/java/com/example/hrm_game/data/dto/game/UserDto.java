package com.example.hrm_game.data.dto.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String photo;
    @JsonProperty("current_positions")
    private List<String> currentPositions;
    @JsonProperty("current_project")
    private String currentProject;
    @JsonProperty("current_city")
    private String currentCity;
    private String guild;
    private String gender;
    private String birth;
    @JsonProperty("joining_date")
    private LocalDate joiningDate;
    private String city;
    private String phone;
    private String email;
    private String courses;
    private String citizenship;
    @JsonProperty("organization_names")
    private List<String> organizationNames;
    @JsonProperty("is_active")
    private Boolean isActive;
    private Integer coins;
    private Integer defeat;
    private Integer victories;
    private AchievementDto achievement;
    private String description;

    public UserDto() {
    }
}
