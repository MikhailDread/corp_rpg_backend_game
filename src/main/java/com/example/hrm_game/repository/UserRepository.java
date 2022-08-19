package com.example.hrm_game.repository;

import com.example.hrm_game.data.entity.game.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityById(Long id);
    UserEntity findUserEntityByLogin(String login);
}
