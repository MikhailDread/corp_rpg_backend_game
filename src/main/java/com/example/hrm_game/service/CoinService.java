package com.example.hrm_game.service;

import com.example.hrm_game.data.entity.game.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoinService {
    @Autowired
    private UserService userService;

    /**
     * Функция добавления игровой валюты пользователю
     *
     * @param userId - id пользователя
     * @param coins  - кол-во валюты
     */
    public void addCoinsForUser(final Long userId, final Integer coins) {
        actionsWithMoney(userId, coins, true);
    }

    /**
     * Функция списания игровой валюты у пользователя
     *
     * @param userId - id пользователя
     * @param coins  - кол-во валюты
     */
    public void writeOffCoinsFromUser(final Long userId, final Integer coins) {
        actionsWithMoney(userId, coins, false);
    }

    /**
     * Функция калькуляции игровой валюты на аккаунте пользователя
     *
     * @param userId          - id пользователя
     * @param coins           - кол-во валюты
     * @param isReplenishment - условие калькуляции (добавление/списание)
     */
    private void actionsWithMoney(final Long userId, final Integer coins, final boolean isReplenishment) {
        UserEntity user = userService.getUserById(userId);
        int currentBalance = user.getCoins();
        if (isReplenishment) {
            currentBalance = currentBalance + coins;
        } else {
            //TODO: Сделать логику траты этих коинов на покупку
            currentBalance = currentBalance - coins;
        }
        user.setCoins(currentBalance);
        userService.updateUser(user);
    }
}
