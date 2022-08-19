package com.example.hrm_game.repository;

import com.example.hrm_game.data.entity.equipment.LeggingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeggingsRepository extends JpaRepository<LeggingsEntity, Long> {
    LeggingsEntity findLeggingsEntityById(Long id);
}
