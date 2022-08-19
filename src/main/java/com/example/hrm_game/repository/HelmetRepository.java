package com.example.hrm_game.repository;

import com.example.hrm_game.data.entity.equipment.HelmetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelmetRepository extends JpaRepository<HelmetEntity, Long> {
    HelmetEntity findHelmetEntityById(Long id);
}
