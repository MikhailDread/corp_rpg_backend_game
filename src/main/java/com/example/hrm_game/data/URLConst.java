package com.example.hrm_game.data;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@AllArgsConstructor
public class URLConst {
    private final Environment env;
    public static String HTTP = "http";
    public static String HOST;
    public static Integer PORT;
    public static String API_ACCOUNTS;
    public static String API_EMPLOYEE;

    @PostConstruct
    public void setConst(){
        HOST = env.getProperty("hrm.http.host");
        PORT = Integer.valueOf(Objects.requireNonNull(env.getProperty("hrm.http.port")));
        API_ACCOUNTS = env.getProperty("hrm.http.api.accounts");
        API_EMPLOYEE = env.getProperty("hrm.http.api.employees");
    }
}
