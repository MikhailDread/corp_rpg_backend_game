package com.example.hrm_game.data.entity.equipment;

import com.example.hrm_game.data.entity.game.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "leggings")
@RequiredArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Leggings entity")
public class LeggingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Leggings id generated by db")
    private Long id;
    @ApiModelProperty(value = "Leggings icon")
    private String image;
    @ApiModelProperty(value = "Leggings name", example = "Leggings of the Wind")
    private String name;
    @ApiModelProperty(value = "Leggings description", example = "Faster than Hermes")
    private String description;
    @ApiModelProperty(value = "Armor amount")
    private Long armor;
    @ApiModelProperty(value = "Strength amount")
    private Long strength;
    @ApiModelProperty(value = "Mana amount")
    private Long mana;
    @ApiModelProperty(value = "Health amount")
    private Long health;
    @ApiModelProperty(value = "Agility amount")
    private Long agility;
    @ApiModelProperty(value = "Intelligence amount")
    private Long intelligence;
    @OneToMany(mappedBy = "leggings")
    private List<UserEntity> user;
    @ManyToMany(mappedBy = "leggings")
    private Set<InventoryEntity> inventory;
}
