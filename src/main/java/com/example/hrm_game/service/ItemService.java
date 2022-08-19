package com.example.hrm_game.service;

import com.example.hrm_game.data.dto.game.ItemDto;
import com.example.hrm_game.data.entity.equipment.*;
import com.example.hrm_game.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ChestRepository chestEntityRepository;
    @Autowired
    private DefenderRepository defenderRepository;
    @Autowired
    private HelmetRepository helmetRepository;
    @Autowired
    private LeggingsRepository leggingsRepository;
    @Autowired
    private WeaponRepository weaponRepository;

    /**
     * Функция получения нагрудника по id
     *
     * @param item - ДТО с данными предмета
     * @return - сущность предмета
     */
    public ChestEntity getChestById(ItemDto item) {
        return chestEntityRepository.findChestEntityById(item.getId());
    }

    /**
     * Функция получения предмета защиты щит/оберег и т.д. по id
     *
     * @param item - ДТО с данными предмета
     * @return - сущность предмета
     */
    public DefenderEntity getDefenderById(ItemDto item) {
        return defenderRepository.findDefenderEntityById(item.getId());
    }

    /**
     * Функция получения шлема по id
     *
     * @param item - ДТО с данными предмета
     * @return - сущность предмета
     */
    public HelmetEntity getHelmetById(ItemDto item) {
        return helmetRepository.findHelmetEntityById(item.getId());
    }

    /**
     * Функция получения поножей по id
     *
     * @param item - ДТО с данными предмета
     * @return - сущность предмета
     */
    public LeggingsEntity getLeggingsById(ItemDto item) {
        return leggingsRepository.findLeggingsEntityById(item.getId());
    }

    /**
     * Функция получения оружия по id
     *
     * @param item - ДТО с данными предмета
     * @return - сущность предмета
     */
    public WeaponEntity getWeaponById(ItemDto item) {
        return weaponRepository.findWeaponEntityById(item.getId());
    }
}
