package com.example.hrm_game.security.repository;

import com.example.hrm_game.data.entity.game.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findRoleEntityByRoleName(String name);
}
