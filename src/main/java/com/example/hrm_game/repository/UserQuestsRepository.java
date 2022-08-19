package com.example.hrm_game.repository;

import com.example.hrm_game.data.entity.game.UsersQuestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestsRepository extends JpaRepository<UsersQuestsEntity, Long> {
    List<UsersQuestsEntity> findUsersQuestEntityByUserId(Long userId);
}
