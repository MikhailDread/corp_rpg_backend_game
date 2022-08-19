package com.example.hrm_game.data.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_role")
@RequiredArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Association of user and role for granting rights")
public class RoleEntity {
    @Id
    @ApiModelProperty(value = "ID for role")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private List<UserEntity> userRole;
    @Column(name = "role_name")
    @ApiModelProperty(value = "Role name from auth process")
    private String roleName;
}
