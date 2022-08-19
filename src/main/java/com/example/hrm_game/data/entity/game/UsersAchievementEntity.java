package com.example.hrm_game.data.entity.game;

import com.example.hrm_game.data.serializable.UsersAchievementEntityId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users_achievement")
@RequiredArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(UsersAchievementEntityId.class)
@ApiModel(description = "User and achievement chain")
public class UsersAchievementEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ApiModelProperty(value = "User that it has achievement")
    private UserEntity user;
    @Id
    @ManyToOne
    @JoinColumn(name = "achieve_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Achievement entity")
    private AchievementEntity achievement;
    @Column(name = "is_added")
    @ApiModelProperty(value = "Added status")
    private boolean isAdded;
    @ApiModelProperty(value = "Total progress to add achievements")
    private Integer total;
    @ApiModelProperty(value = "Achievement progress")
    private Integer progress;
}
