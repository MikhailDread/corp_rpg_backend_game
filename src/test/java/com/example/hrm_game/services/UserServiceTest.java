package com.example.hrm_game.services;

import com.example.hrm_game.data.entity.game.UserEntity;
import com.example.hrm_game.repository.UserRepository;
import com.example.hrm_game.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(45L);
        user.setLogin("mokito_test");
        user.setPassword("1234567");
        user.setCoins(100);
        user.setName("mokito_test");
    }

    @AfterEach
    void tearDown(){
        user = null;
    }

    @Test
    void updateUserTest(){
        userService.updateUser(user);
        verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void getUserByLoginTest(){
        userService.getUserByLogin(user.getLogin());
        verify(userRepository, Mockito.times(1)).findUserEntityByLogin(user.getLogin());
    }

    @Test
    void getUserByIdTest(){
        userService.getUserById(user.getId());
        verify(userRepository, Mockito.times(1)).findUserEntityById(user.getId());
    }

    @Test
    void findUserByLoginAndPasswordAndMatchesTest(){
        userService.findUserByLoginAndPasswordAndMatches(user.getLogin(), user.getPassword());
        verify(userService, Mockito.times(1)).getUserByLogin(user.getLogin());
        verify(passwordEncoder, Mockito.times(1)).matches("1234567", user.getPassword());
    }
}
