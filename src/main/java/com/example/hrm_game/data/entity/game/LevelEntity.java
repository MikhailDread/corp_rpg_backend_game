package com.example.hrm_game.data.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "levels")
@RequiredArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "User level entity")
public class LevelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Level id generated by db")
    private Long id;
    @ApiModelProperty(value = "Current user level")
    private Integer level;
    @Column(name = "current_experience")
    @ApiModelProperty(value = "Current user experience")
    private Integer currentExperience;
    @Column(name = "max_experience")
    @ApiModelProperty(value = "Max experience for current user level")
    private Integer maxExperience;
    @OneToOne(mappedBy = "level", orphanRemoval = true)
    private UserEntity userEntity;
}
