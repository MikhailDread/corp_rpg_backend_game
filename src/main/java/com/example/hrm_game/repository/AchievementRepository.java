package com.example.hrm_game.repository;

import com.example.hrm_game.data.entity.game.AchievementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<AchievementEntity, Long> {
    AchievementEntity findAchievementEntityById(Long id);
}
