package com.example.hrm_game.controller;

import com.example.hrm_game.data.dto.game.UserDto;
import com.example.hrm_game.data.entity.game.UserEntity;
import com.example.hrm_game.service.UserService;
import com.example.hrm_game.service.http.HttpRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.example.hrm_game.data.URLConst.*;

@RestController
@Api("Controller of add and update users data in HRM/game system")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful request"),
        @ApiResponse(code = 403, message = "User not authorized"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 503, message = "HRM service is drop")
})
public class UserRestController {
    @Autowired
    private UserService accountsService;
    @Autowired
    private HttpRequestService httpRequestService;
    @Autowired
    private HttpUrl.Builder builder;

    @GetMapping("/user/{id}")
    @SneakyThrows
    @ApiOperation("Get user by id out of db")
    public ResponseEntity<UserEntity> getUser(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(accountsService.getUserById(userId));
    }

    @PostConstruct
    @SneakyThrows
    @ApiOperation("Update and add users out of HRM after start application")
    public void initAllAccountsBeforeStartServer() {
        startInitDataBase();
    }

    @Scheduled(cron = "0 0 0 * * *")
    @SneakyThrows
    @ApiOperation("Init update users from cron config")
    public void updateUsersDataScheduled() {
        startInitDataBase();
    }

    private void startInitDataBase() {
        String url = builder.scheme(HTTP)
                .host(HOST)
                .port(PORT)
                .addPathSegments(API_EMPLOYEE).toString();
//        ResponseEntity<String> response = httpRequestService.createExchangeRequest(
//                url,
//                HttpMethod.GET,
//                httpRequestService.requestBody("body")
//        );
//        List<UserDto> users = JacksonParser.getObjectMapper().readValue(
//        response.getBody(),
//        new TypeReference<List<UserDto>>() {}
//        );
        List<UserDto> users = new ArrayList<>();
        accountsService.insertAllUser(users);
    }
}
