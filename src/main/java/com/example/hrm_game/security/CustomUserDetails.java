package com.example.hrm_game.security;

import com.example.hrm_game.data.entity.game.UserEntity;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public static CustomUserDetails fromUserEntityToCustomUserDetails(UserEntity userEntity) {
        CustomUserDetails customerUser = new CustomUserDetails();
        customerUser.login = userEntity.getLogin();
        customerUser.password = userEntity.getPassword();
        customerUser.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().getRoleName()));
        return customerUser;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static String hasRole(String role){
        Assert.notNull(role, "Role cannot be null");
        if(role.startsWith("ROLE_")){
            throw new IllegalArgumentException(
                    "Role should not start with 'ROLE_' since it is auto inserted. Got '" + role + "'"
            );
        }
        return "hasRole('ROLE_" + role + "')";
    }
}
