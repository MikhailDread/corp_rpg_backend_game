package com.example.hrm_game.data.entity.game;

import com.example.hrm_game.data.entity.equipment.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@RequiredArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "User entity")
public class UserEntity {
    /**
     * Users information fields
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "User id add by HRM")
    private Long id;
    @ApiModelProperty(value = "User login from authorization form")
    private String login;
    @ApiModelProperty(value = "User password from authorization form")
    private String password;
    @ApiModelProperty(value = "User name", example = "John")
    private String name;
    @ApiModelProperty(value = "User project", example = "LANIT Expertise")
    private String guild;
    @ApiModelProperty(value = "User reputation")
    private Integer reputation;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_level", referencedColumnName = "id")
    private LevelEntity level;
    @ApiModelProperty(value = "User's money")
    @Value("${some.key:0}")
    private int coins;
    @ApiModelProperty(value = "User's defeat")
    @Value("${some.key:0}")
    private int defeat;
    @ApiModelProperty(value = "User's victories")
    @Value("${some.key:0}")
    private int victories;
    @ApiModelProperty(value = "User's health stat")
    @Value("${some.key:0}")
    private int health;
    @ApiModelProperty(value = "User's mana stat")
    @Value("${some.key:0}")
    private int mana;
    @ApiModelProperty(value = "User's armor stat")
    @Value("${some.key:0}")
    private int armor;
    @ApiModelProperty(value = "User's strength stat")
    @Value("${some.key:0}")
    private int strength;
    @ApiModelProperty(value = "User's agility stat")
    @Value("${some.key:0}")
    private int agility;
    @ApiModelProperty(value = "User's intelligence stat")
    @Value("${some.key:0}")
    private int intelligence;
    @OneToMany(mappedBy = "user")
    private List<UsersAchievementEntity> usersAchievements;
    @OneToMany(mappedBy = "user")
    private List<UsersQuestsEntity> questEntities;
    /**
     * Users HRM fields
     */
    @ApiModelProperty(value = "User description", example = "Hello! I'm John. I work QA Automation")
    private String description;
    @ApiModelProperty(value = "User photo")
    private String photo;
    @JsonProperty("current_positions")
    @Column(name = "current_positions")
    @ElementCollection
    private List<String> currentPositions;
    @JsonProperty("current_project")
    @Column(name = "current_project")
    private String currentProject;
    @ApiModelProperty(value = "User's gender")
    private String gender;
    @ApiModelProperty(value = "User's birth")
    private String birth;
    @JsonProperty("joining_date")
    @Column(name = "joining_date")
    @ApiModelProperty(value = "User's joining date to game")
    private LocalDate joiningDate;
    @ApiModelProperty(value = "User's city")
    private String city;
    @JsonProperty("is_active")
    @Column(name = "is_active")
    @ApiModelProperty(value = "User's active status")
    private Boolean isActive;
    /**
     * Users equipment fields
     */
    @ManyToOne
    @JoinColumn(name = "user_helmet", referencedColumnName = "id")
    private HelmetEntity helmet;
    @ManyToOne
    @JoinColumn(name = "user_chest", referencedColumnName = "id")
    private ChestEntity chest;
    @ManyToOne
    @JoinColumn(name = "user_leggings", referencedColumnName = "id")
    private LeggingsEntity leggings;
    @ManyToOne
    @JoinColumn(name = "user_weapon", referencedColumnName = "id")
    private WeaponEntity weapon;
    @ManyToOne
    @JoinColumn(name = "user_defender", referencedColumnName = "id")
    private DefenderEntity defender;
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private InventoryEntity inventoryEntity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_role", referencedColumnName = "id")
    private RoleEntity role;
}
