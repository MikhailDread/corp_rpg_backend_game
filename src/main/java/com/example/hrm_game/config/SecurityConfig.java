package com.example.hrm_game.config;

import com.example.hrm_game.security.JwtFilter;
import com.example.hrm_game.security.data.JWTBlackList;
import com.example.hrm_game.security.repository.JWTBlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private JWTBlackListRepository jwtBlackListRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/moderator/*").hasRole("MODERATOR")
                .antMatchers("/register", "/auth", "/swagger-ui/").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout").invalidateHttpSession(true)
                .logoutSuccessHandler(
                        new LogoutSuccessHandler() {
                            @Override
                            public void onLogoutSuccess(
                                    HttpServletRequest request,
                                    HttpServletResponse response,
                                    Authentication authentication
                            ) {
                                String token;
                                System.out.println();
                                if(request.getRequestURI().contains("/logout")){
                                    token = jwtFilter.getTokenFromRequest(request);
                                    JWTBlackList tokenInBlackList =
                                            jwtBlackListRepository.findJWTBlackListByToken(token);
                                    if (tokenInBlackList == null) {
                                        JWTBlackList blackList = new JWTBlackList();
                                        blackList.setToken(token);
                                        blackList.setAddBlackListDate(LocalDate.now());
                                        jwtBlackListRepository.save(blackList);
                                    }
                                }
                                //TODO: на случай работы с куки
                                Cookie[] cookies = request.getCookies();
//                                for (Cookie cookie : cookies) {
//                                    if (cookie.getName().equals("token")) {
//                                        token = cookie.getValue();
//                                        JWTBlackList tokenInBlackList =
//                                                jwtBlackListRepository.findJWTBlackListByToken(token);
//                                        if (tokenInBlackList == null) {
//                                            JWTBlackList blackList = new JWTBlackList();
//                                            blackList.setToken(token);
//                                            jwtBlackListRepository.save(blackList);
//                                        }
//                                    }
//                                }
                            }
                        })
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
