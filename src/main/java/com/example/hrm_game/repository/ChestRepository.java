package com.example.hrm_game.repository;

import com.example.hrm_game.data.entity.equipment.ChestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChestRepository extends JpaRepository<ChestEntity, Long> {
    ChestEntity findChestEntityById(Long id);
}
