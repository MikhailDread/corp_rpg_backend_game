package com.example.hrm_game.repository;

import com.example.hrm_game.data.entity.hrm.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    AccountEntity findAccountEntityById(Long id);
}
