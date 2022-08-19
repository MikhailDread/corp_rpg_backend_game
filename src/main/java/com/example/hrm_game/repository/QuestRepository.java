package com.example.hrm_game.repository;

import com.example.hrm_game.data.entity.game.QuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends JpaRepository<QuestEntity, Long> {
    QuestEntity findQuestEntityById(Long id);
}
