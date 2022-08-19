package com.example.hrm_game.service;

import com.example.hrm_game.data.dto.game.ItemDto;
import com.example.hrm_game.data.dto.game.QuestDto;
import com.example.hrm_game.data.entity.equipment.*;
import com.example.hrm_game.data.entity.game.UserEntity;
import com.example.hrm_game.repository.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;
import java.util.*;

import static com.example.hrm_game.data.dto.game.ItemEnum.*;

@Service
public class UserInventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    /**
     * Функция добавления нового предмета в инвентарь пользователя
     *
     * @param userId   - id пользователя
     * @param questDto - ДТО, описывающее критерии текущего квеста
     */
    public void addItemInUserInventory(Long userId, QuestDto questDto) {
        InventoryEntity userInventory = getUserInventory(userService.getUserById(userId));

        questDto.getAwards().forEach(
                award -> {
                    if (award.getItemEnum().getType().equals(CHEST.getType())) {
                        setItemInInventory(userInventory, itemService.getChestById(award));
                    } else if (award.getItemEnum().getType().equals(DEFENDER.getType())) {
                        setItemInInventory(userInventory, itemService.getDefenderById(award));
                    } else if (award.getItemEnum().getType().equals(HELMET.getType())) {
                        setItemInInventory(userInventory, itemService.getHelmetById(award));
                    } else if (award.getItemEnum().getType().equals(LEGGINGS.getType())) {
                        setItemInInventory(userInventory, itemService.getLeggingsById(award));
                    } else if (award.getItemEnum().getType().equals(WEAPON.getType())) {
                        setItemInInventory(userInventory, itemService.getWeaponById(award));
                    }
                    inventoryRepository.save(userInventory);
                }
        );
    }

    /**
     * Функция добавления предмета в слот персонажа пользователя из инвентаря
     *
     * @param idUser - id пользователя
     * @param item   - ДТО предмета с его описанием
     */
    @SneakyThrows
    public void putItemToUserById(Long idUser, ItemDto item) {
        UserEntity user = userService.getUserById(idUser);
        InventoryEntity userInventory = getUserInventory(user);

        if (item.getItemEnum().getType().equals(WEAPON.getType())) {
            userInventory.getWeapons().forEach(weapon -> {
                        if (weapon.getId().equals(item.getId())) {
                            if (user.getWeapon() == null) {
                                user.setWeapon(weapon);
                                calculateStatForUser(user, weapon, true);
                            } else {
                                setItemInInventory(userInventory, user.getWeapon());
                                calculateStatForUser(user, weapon, false);
                                user.setWeapon(weapon);
                                calculateStatForUser(user, weapon, true);
                            }
                            deleteItemOutInventory(userInventory, weapon);
                        }
                    }
            );
        } else if (item.getItemEnum().getType().equals(CHEST.getType())) {
            userInventory.getChests().forEach(chest -> {
                        if (chest.getId().equals(item.getId())) {
                            if (user.getChest() == null) {
                                user.setChest(chest);
                                calculateStatForUser(user, chest, true);
                            } else {
                                setItemInInventory(userInventory, user.getChest());
                                calculateStatForUser(user, chest, false);
                                user.setChest(chest);
                                calculateStatForUser(user, chest, true);

                            }
                            //TODO: проверить: при первом добавлении шмотки на пользователя и падает на нулл вместо удаления
                            deleteItemOutInventory(userInventory, chest);
                        }
                    }
            );
        } else if (item.getItemEnum().getType().equals(DEFENDER.getType())) {
            userInventory.getDefenders().forEach(defender -> {
                        if (defender.getId().equals(item.getId())) {
                            if (user.getDefender() == null) {
                                user.setDefender(defender);
                                calculateStatForUser(user, defender, true);
                            } else {
                                setItemInInventory(userInventory, user.getDefender());
                                calculateStatForUser(user, defender, false);
                                user.setDefender(defender);
                                calculateStatForUser(user, defender, true);
                            }
                            deleteItemOutInventory(userInventory, defender);
                        }
                    }
            );
        } else if (item.getItemEnum().getType().equals(LEGGINGS.getType())) {
            userInventory.getLeggings().forEach(leggings -> {
                        if (leggings.getId().equals(item.getId())) {
                            if (user.getLeggings() == null) {
                                user.setLeggings(leggings);
                                calculateStatForUser(user, leggings, true);
                            } else {
                                setItemInInventory(userInventory, user.getLeggings());
                                calculateStatForUser(user, leggings, false);
                                user.setLeggings(leggings);
                                calculateStatForUser(user, leggings, true);
                            }
                            deleteItemOutInventory(userInventory, leggings);
                        }
                    }
            );
        } else if (item.getItemEnum().getType().equals(HELMET.getType())) {
            userInventory.getHelmets().forEach(helmet -> {
                        if (helmet.getId().equals(item.getId())) {
                            if (user.getHelmet() == null) {
                                user.setHelmet(helmet);
                                calculateStatForUser(user, helmet, true);
                            } else {
                                setItemInInventory(userInventory, user.getHelmet());
                                calculateStatForUser(user, helmet, false);
                                user.setHelmet(helmet);
                                calculateStatForUser(user, helmet, true);
                            }
                            deleteItemOutInventory(userInventory, helmet);
                        }
                    }
            );
        }
        userService.updateUser(user);
        saveUserInventory(userInventory);
    }

    /**
     * Функция удаления предмета из инвентаря пользователя
     *
     * @param userId - id пользователя
     * @param item   - ДТО предмета с его описанием
     */
    public void deleteItemFromUserInventory(Long userId, ItemDto item) {
        InventoryEntity userInventory =
                getUserInventory(
                        userService.getUserById(userId)
                );

        if (item.getItemEnum().getType().equals(WEAPON.getType())) {
            userInventory.getWeapons().remove(itemService.getWeaponById(item));
        } else if (item.getItemEnum().getType().equals(CHEST.getType())) {
            userInventory.getChests().remove(itemService.getChestById(item));
        } else if (item.getItemEnum().getType().equals(DEFENDER.getType())) {
            userInventory.getDefenders().remove(itemService.getDefenderById(item));
        } else if (item.getItemEnum().getType().equals(LEGGINGS.getType())) {
            userInventory.getLeggings().remove(itemService.getLeggingsById(item));
        } else if (item.getItemEnum().getType().equals(HELMET.getType())) {
            userInventory.getHelmets().remove(itemService.getHelmetById(item));
        }
        saveUserInventory(userInventory);
    }

    /**
     * Функция осуществляющая перемещения предмета у пользователя
     *
     * @param userInventory - инвентарь пользователя
     * @param item          - ДТО предмета с его описанием
     */
    @SneakyThrows
    private void setItemInInventory(InventoryEntity userInventory, Object item) {
        for (Field field : userInventory.getClass().getDeclaredFields()) {
            if (field.getGenericType().getTypeName().contains(item.getClass().getTypeName())) {
                field.setAccessible(true);
                Set<Object> objects = new HashSet<>((Collection) field.get(userInventory));
                objects.add(item);
                field.set(userInventory, objects);
            }
        }
    }

    /**
     * Функция калькуляции характеристик при надевании/снятии предмета с пользователя
     *
     * @param user         - сущность пользователя из БД
     * @param item         - ДТО предмета с его описанием
     * @param isAdditional - условия добавлен/не добавлен предмет
     */
    @SneakyThrows
    private <T> void calculateStatForUser(UserEntity user, T item, boolean isAdditional) {
        for (Field userField : user.getClass().getDeclaredFields()) {
            for (Field itemField : user.getChest().getClass().getDeclaredFields()) {
                if (userField.getName().equals("id") ||
                        userField.getName().equals("name") ||
                        userField.getName().equals("description")
                ) {
                    continue;
                } else if (userField.getName().equals(itemField.getName())) {
                    userField.setAccessible(true);
                    itemField.setAccessible(true);
                    int sum;
                    if (isAdditional) {
                        sum = Integer.parseInt(userField.get(user).toString())
                                + Integer.parseInt(itemField.get(item).toString());
                    } else {
                        sum = Integer.parseInt(userField.get(user).toString())
                                - Integer.parseInt(itemField.get(item).toString());
                        if (sum < 0) {
                            sum = 0;
                        }
                    }
                    userField.set(user, sum);
                }
            }
        }
    }

    /**
     * Функция удаления предмета из инвентаря при надевании на персонажа пользователя
     *
     * @param userInventory - инвентарь пользователя
     * @param oldItem       - старый предмет
     */
    @SneakyThrows
    private <T> void deleteItemOutInventory(InventoryEntity userInventory, T oldItem) {
        for (Field field : userInventory.getClass().getDeclaredFields()) {
            if (field.getGenericType().getTypeName().contains(oldItem.getClass().getTypeName())) {
                field.setAccessible(true);
                if (Set.class.isAssignableFrom(field.getType())) {
                    Set itemsInInventory = (Set) ReflectionTestUtils.getField(userInventory, field.getName());
                    Objects.requireNonNull(itemsInInventory).remove(oldItem);
                }
            }
        }
        saveUserInventory(userInventory);
    }

    /**
     * Функция получения инвентаря пользователя
     *
     * @param user - сущность пользователя из БД
     * @return - инвентарь пользователя
     */
    public InventoryEntity getUserInventory(UserEntity user) {
        return inventoryRepository.findInventoryEntityByUser(user);
    }

    /**
     * Функция сохранения обновленного инвентаря пользователя
     *
     * @param userInventory - сущность инвентаря пользователя из БД
     */
    public void saveUserInventory(InventoryEntity userInventory) {
        inventoryRepository.save(userInventory);
    }
}
