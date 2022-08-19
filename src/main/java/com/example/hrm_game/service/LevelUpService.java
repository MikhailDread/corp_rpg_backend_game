package com.example.hrm_game.service;

import com.example.hrm_game.data.entity.game.LevelEntity;
import com.example.hrm_game.data.entity.game.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LevelUpService {
    @Autowired
    private UserService userService;

    /**
     * Функция прогресса получения опыта у пользователя
     *
     * @param userId     - id пользователя
     * @param experience - кол-во полученного опыта
     */
    public void experienceProgress(final Long userId, final Integer experience) {
        //TODO: отрефакторить по возможности это
        UserEntity user = userService.getUserById(userId);
        LevelEntity level = user.getLevel();
        Integer progressUp = level.getCurrentExperience() + experience;
        if (progressUp > level.getMaxExperience()) {
            int excess = progressUp - level.getMaxExperience();
            upUserLevel(user, level, excess);
        } else if (progressUp.equals(level.getMaxExperience())) {
            upUserLevel(user, level, 0);
        } else {
            level.setCurrentExperience(level.getCurrentExperience() + experience);
            userService.updateUser(user);
        }
    }

    /**
     * Функция получения уровня пользователя
     *
     * @param user   - сущность пользователя из БД приложения
     * @param level  - сущность нового уровня из Бд приложения
     *               с новыми требованиями достижения максимум
     * @param excess - избыток опыта для переливки на новый уровень
     */
    public void upUserLevel(UserEntity user, LevelEntity level, int excess) {
        //TODO: отрефакторить о возможности этого
        if (excess > 0) {
            do {
                level.setLevel(level.getLevel() + 1);
                Integer levelCurrent = level.getLevel();
                Double exp = level.getMaxExperience() * calculateCoefficientLevels(levelCurrent);
                level.setCurrentExperience(excess);
                level.setMaxExperience(level.getMaxExperience() + exp.intValue());
                excess = excess - level.getMaxExperience();
                if (excess > 0) {
                    Integer currentLevel = level.getLevel();
                    Double insideNewExpLevel = level.getMaxExperience() * calculateCoefficientLevels(currentLevel);
                    level.setLevel(level.getLevel() + 1);
                    level.setCurrentExperience(excess);
                    level.setMaxExperience(level.getMaxExperience() + insideNewExpLevel.intValue());
                }
            } while (excess > level.getMaxExperience());
        } else {
            Integer currentLevel = level.getLevel();
            Double newExpLevel = level.getMaxExperience() * calculateCoefficientLevels(currentLevel);
            level.setLevel(level.getLevel() + 1);
            level.setCurrentExperience(0);
            level.setMaxExperience(level.getMaxExperience() + newExpLevel.intValue());
        }
        userService.updateUser(user);
    }

    /**
     * Функция получения коэффициента уровня
     * для калькуляции прогресс-бара уровня
     *
     * @param currentLevel - текущий уровень
     * @return - коэффициент
     */
    private Double calculateCoefficientLevels(int currentLevel) {
        return new BigDecimal(5).multiply(new BigDecimal(currentLevel)).divide(new BigDecimal(100)).doubleValue();
    }
}
