package com.example.hrm_game.repository;

import com.example.hrm_game.data.entity.equipment.WeaponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponRepository extends JpaRepository<WeaponEntity, Long> {
    WeaponEntity findWeaponEntityById(Long id);
}
