package com.example.hrm_game.repository;

import com.example.hrm_game.data.entity.equipment.InventoryEntity;
import com.example.hrm_game.data.entity.game.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    InventoryEntity findInventoryEntityByUser(UserEntity userInventory);
    InventoryEntity findInventoryEntityById(Long id);
}
