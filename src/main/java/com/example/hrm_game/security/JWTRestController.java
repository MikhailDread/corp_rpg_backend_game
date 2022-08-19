package com.example.hrm_game.security;

import com.example.hrm_game.security.data.JWTBlackList;
import com.example.hrm_game.security.repository.JWTBlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class JWTRestController {
    @Autowired
    private JWTBlackListRepository jwtBlackListRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void clearOldJWTInBlackList() {
        List<JWTBlackList> jwtForClear = new ArrayList<>();
        for (JWTBlackList blackJWT : jwtBlackListRepository.findAll()) {
            LocalDate addBlackListDate = blackJWT.getAddBlackListDate();
            if (addBlackListDate.isBefore(LocalDate.now().minusDays(15))) {
                jwtForClear.add(blackJWT);
            }
        }
        jwtBlackListRepository.deleteAll(jwtForClear);
    }
}
