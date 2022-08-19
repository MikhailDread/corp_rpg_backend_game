package com.example.hrm_game.services;

import com.example.hrm_game.data.dto.game.AchievementDto;
import com.example.hrm_game.data.entity.game.AchievementEntity;
import com.example.hrm_game.data.entity.game.UserEntity;
import com.example.hrm_game.data.entity.game.UsersAchievementEntity;
import com.example.hrm_game.repository.UserAchievementRepository;
import com.example.hrm_game.service.AchievementService;
import com.example.hrm_game.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {
    @InjectMocks
    private AchievementService achievementService;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private UserService userService;
    private UsersAchievementEntity usersAchievement;
    private UserEntity user;
    private AchievementDto achievementDto;
    private AchievementEntity achievement;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(45L);
        user.setLogin("mokito_test");
        user.setPassword("1234567");
        user.setCoins(100);
        user.setName("mokito_test");
        achievement = new AchievementEntity();
        achievement.setId(1L);
        usersAchievement = new UsersAchievementEntity();
        usersAchievement.setAchievement(achievement);
        usersAchievement.setProgress(100);
        usersAchievement.setTotal(50);
        usersAchievement.setUser(user);
        user.setUsersAchievements(Collections.singletonList(usersAchievement));
        achievementDto = new AchievementDto(1L, 100);
        achievementDto.setProgress(100);

    }

    @AfterEach
    void tearDown() {
        usersAchievement = null;
        user = null;
        achievementDto = null;
        achievement = null;
    }

    @Test
    void saveUserAchievementTest() {
        achievementService.saveUserAchievement(usersAchievement);
        verify(userAchievementRepository, Mockito.times(1)).save(usersAchievement);
    }

    @Test
    void getUserAchievementsTest() {
        achievementService.getUserAchievements(user);
        verify(userAchievementRepository,
                Mockito.times(1))
                .findUsersAchievementEntityByUserId(user.getId());
    }

    @Test
    void progressAchievementAtUserTest() {
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(userAchievementRepository.findUsersAchievementEntityByUserId(user.getId()))
                .thenReturn(Collections.singletonList(usersAchievement));
        boolean trueAdding = achievementService.progressAchievementAtUser(user.getId(), achievementDto);
        assertTrue(trueAdding);
        usersAchievement.setTotal(500);
        boolean falseAdding = achievementService.progressAchievementAtUser(user.getId(), achievementDto);
        assertFalse(falseAdding);
    }
}
