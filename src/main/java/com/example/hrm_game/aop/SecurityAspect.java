package com.example.hrm_game.aop;

import com.example.hrm_game.data.entity.game.UserEntity;
import com.example.hrm_game.security.JwtFilter;
import com.example.hrm_game.security.JwtProvider;
import com.example.hrm_game.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SecurityAspect {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private UserService userService;

    @Pointcut(value = "within(com.example.hrm_game.controller.*)")
    public void authUser() {
    }

    @Before(value = "args(userId, .., servletRequest) && authUser()")
    public void authProcess(Long userId, ServletRequest servletRequest) {
        String token = jwtFilter.getTokenFromRequest((HttpServletRequest) servletRequest);
        String userLogin = jwtProvider.getLoginFromToken(token, servletRequest);
        UserEntity user = userService.getUserById(userId);
        if(user.getLogin().equals(userLogin)){
            log.info("The user who made the request correctly performs the operation");
        } else {
            throw new UsernameNotFoundException("The user performs an unidentified operation!");
        }
    }
}
