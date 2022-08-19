package com.example.hrm_game.data.entity.equipment;

import com.example.hrm_game.data.entity.game.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "inventory")
@RequiredArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
//TODO: доделать Сваггер тут
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_inventory",
            joinColumns = @JoinColumn(name = "inventory_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private UserEntity user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "chest_in_inventory",
            joinColumns = @JoinColumn(name = "inventory_id"),
            inverseJoinColumns = @JoinColumn(name = "chest_id")
    )
    private Set<ChestEntity> chests;
        @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "defender_in_inventory",
            joinColumns = @JoinColumn(name = "inventory_id"),
            inverseJoinColumns = @JoinColumn(name = "defender_id")
    )
    private Set<DefenderEntity> defenders;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "helmet_in_inventory",
            joinColumns = @JoinColumn(name = "inventory_id"),
            inverseJoinColumns = @JoinColumn(name = "helmet_id")
    )
    private Set<HelmetEntity> helmets;
        @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "leggings_in_inventory",
            joinColumns = @JoinColumn(name = "inventory_id"),
            inverseJoinColumns = @JoinColumn(name = "leggings_id")
    )
    private Set<LeggingsEntity> leggings;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "weapon_in_inventory",
            joinColumns = @JoinColumn(name = "inventory_id"),
            inverseJoinColumns = @JoinColumn(name = "weapon_id")
    )
    private Set<WeaponEntity> weapons;
    private Integer cells;
}
