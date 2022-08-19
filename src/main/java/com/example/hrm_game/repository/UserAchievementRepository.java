package com.example.hrm_game.repository;

import com.example.hrm_game.data.entity.game.UsersAchievementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAchievementRepository extends JpaRepository<UsersAchievementEntity, Long> {
    List<UsersAchievementEntity> findUsersAchievementEntityByUserId(Long userId);
}
