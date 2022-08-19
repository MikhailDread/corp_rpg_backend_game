package com.example.hrm_game.data.entity.game;

import com.example.hrm_game.data.serializable.UserQuestsEntityId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users_quests")
@RequiredArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(UserQuestsEntityId.class)
@ApiModel(description = "User and quest chain")
public class UsersQuestsEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ApiModelProperty(value = "User that it has quest")
    private UserEntity user;
    @Id
    @ManyToOne
    @JoinColumn(name = "quest_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Quest entity")
    private QuestEntity quests;
    @Column(name = "is_completed")
    @ApiModelProperty(value = "Complete status")
    private boolean isCompleted;
    @ApiModelProperty(value = "Total progress to complete quest")
    private Integer total;
    @ApiModelProperty(value = "Quest progress")
    private Integer progress;
}
