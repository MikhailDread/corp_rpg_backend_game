package com.example.hrm_game.data.entity.equipment;

import com.example.hrm_game.data.entity.game.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "weapon")
@RequiredArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Weapon entity")
public class WeaponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Weapon id generated by db")
    private Long id;
    @ApiModelProperty(value = "Weapon icon")
    private String image;
    @ApiModelProperty(value = "Weapon name", example = "Sword of Calamity")
    private String name;
    @ApiModelProperty(value = "Weapon description", example = "May he bring eternal suffering")
    private String description;
    //TODO: сделать энам
    @ApiModelProperty(value = "Weapon description", example = "SWORD")
    private String type;
    @ApiModelProperty(value = "Strength amount")
    @Value("${some.key:0}")
    private int strength;
    @ApiModelProperty(value = "Agility amount")
    @Value("${some.key:0}")
    private int agility;
    @ApiModelProperty(value = "Intelligence amount")
    @Value("${some.key:0}")
    private int intelligence;
    @ApiModelProperty(value = "Damage amount")
    @Value("${some.key:0}")
    private int damage;
    @OneToMany(mappedBy = "weapon")
    private List<UserEntity> user;
    @ManyToMany(mappedBy = "weapons")
    private Set<InventoryEntity> inventory;
}