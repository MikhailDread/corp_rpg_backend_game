package com.example.hrm_game.service;

import com.example.hrm_game.data.dto.game.AchievementDto;
import com.example.hrm_game.data.entity.game.UserEntity;
import com.example.hrm_game.data.entity.game.UsersAchievementEntity;
import com.example.hrm_game.repository.UserAchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAchievementRepository userAchievementRepository;

    /**
     * Функция добавления ачивки пользователю
     *
     * @param userId      - id пользователя
     * @param achievement - ДТО ачивки
     */
    public void addAchievementForUser(final Long userId, final AchievementDto achievement) {
        UserEntity user = userService.getUserById(userId);

        UsersAchievementEntity usersAchievementEntity = getUserAchievements(user)
                .stream()
                .filter(usr ->
                        usr.getAchievement().getId().equals(achievement.getId()))
                .findAny()
                .get();
        if (!usersAchievementEntity.isAdded()) {
            usersAchievementEntity.setAdded(true);
            saveUserAchievement(usersAchievementEntity);
        }
    }

    /**
     * Функция расчета прогресса получения ачивки и валидация её достижения
     *
     * @param userId      - id пользователя
     * @param achievement - ДТО ачивки
     * @return - статус достижения ачивки
     */
    public boolean progressAchievementAtUser(final Long userId, final AchievementDto achievement) {
        UserEntity user = userService.getUserById(userId);

        UsersAchievementEntity targetAchievement =
                getUserAchievements(user)
                        .stream()
                        .filter(usr ->
                                usr.getAchievement().getId().equals(achievement.getId()))
                        .findAny()
                        .get();
        if (!targetAchievement.isAdded()) {
            targetAchievement.setProgress(targetAchievement.getProgress() + achievement.getProgress());
            if (targetAchievement.getProgress() >= targetAchievement.getTotal()) {
                saveUserAchievement(targetAchievement);
                return true;
            } else {
                saveUserAchievement(targetAchievement);
                return false;
            }
        }
        return false;
    }

    /**
     * Функция получения списка ачивок у пользователя
     *
     * @param user - энтити пользователя из БД приложения
     * @return - список ачивок
     */
    public List<UsersAchievementEntity> getUserAchievements(UserEntity user) {
        return userAchievementRepository.findUsersAchievementEntityByUserId(user.getId());
    }

    /**
     * Сохранение нового состояния ачивки у пользователя
     *
     * @param targetAchievement - конкретная ачивка пользователя
     */
    public void saveUserAchievement(UsersAchievementEntity targetAchievement) {
        userAchievementRepository.save(targetAchievement);
    }
}
