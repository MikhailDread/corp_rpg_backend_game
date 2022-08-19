package com.example.hrm_game.security;

import com.example.hrm_game.data.dto.auth.AuthRequest;
import com.example.hrm_game.data.dto.auth.AuthResponse;
import com.example.hrm_game.data.dto.auth.RegistrationRequest;
import com.example.hrm_game.data.entity.game.RoleEntity;
import com.example.hrm_game.data.entity.game.UserEntity;
import com.example.hrm_game.service.RoleService;
import com.example.hrm_game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.example.hrm_game.data.enums.UsersRoles.USER;

@RestController
public class AuthRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        UserEntity userByLogin = userService.getUserByLogin(registrationRequest.getLogin());
        if (userByLogin != null) {
            return "This username is taken, please try another one";
        }
        UserEntity newUser = new UserEntity();
        newUser.setLogin(registrationRequest.getLogin());
        RoleEntity userRole = roleService.getRole(USER.getName());
        newUser.setRole(userRole);
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userService.updateUser(newUser);
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponse auth(
            @RequestBody AuthRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        UserEntity user = userService.findUserByLoginAndPasswordAndMatches(
                request.getLogin(),
                request.getPassword()
        );
        String token = jwtProvider.generateToken(user.getLogin(), httpServletRequest);
        //TODO: сделал для варианта где токен болтается в куки,
        // в случае одобрения такого подхода переделать извлечение токена вместо Bearer на куки
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        httpServletResponse.addCookie(cookie);
        return new AuthResponse(token);
    }

    @GetMapping("/logout")
    public String logoutUser() {
        return "Logout success";
    }
}
