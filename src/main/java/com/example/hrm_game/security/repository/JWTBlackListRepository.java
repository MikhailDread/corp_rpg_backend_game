package com.example.hrm_game.security.repository;

import com.example.hrm_game.security.data.JWTBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JWTBlackListRepository extends JpaRepository<JWTBlackList, Long> {
    JWTBlackList findJWTBlackListByToken(String token);
}
