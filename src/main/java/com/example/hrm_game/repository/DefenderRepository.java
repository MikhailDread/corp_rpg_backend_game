package com.example.hrm_game.repository;

import com.example.hrm_game.data.entity.equipment.DefenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefenderRepository extends JpaRepository<DefenderEntity, Long> {
    DefenderEntity findDefenderEntityById(Long id);
}
