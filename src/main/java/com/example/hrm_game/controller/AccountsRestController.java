package com.example.hrm_game.controller;

import com.example.hrm_game.data.dto.hrm.AccountDto;
import com.example.hrm_game.service.AccountService;
import com.example.hrm_game.service.http.HttpRequestService;
import com.example.hrm_game.service.utils.JacksonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.example.hrm_game.data.URLConst.*;

@RestController
@Api("Controller of add and update accounts data in HRM/game system")
@DependsOn("URLConst")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful request"),
        @ApiResponse(code = 403, message = "User not authorized"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 503, message = "HRM service is drop")
})
public class AccountsRestController {
    @Autowired
    private AccountService accountsService;
    @Autowired
    private HttpRequestService httpRequestService;
    @Autowired
    private HttpUrl.Builder builder;

    @GetMapping("/accounts")
    @SneakyThrows
    @ApiOperation("Get all accounts out HRM")
    public void getAccounts() {
        String url = builder
                .scheme(HTTP)
                .host(HOST)
                .port(PORT)
                .addPathSegments(API_ACCOUNTS).toString();

        ResponseEntity<String> response = httpRequestService.createRequest(
                url,
                HttpMethod.GET,
                httpRequestService.requestBody("body")
        );
        List<AccountDto> accounts = JacksonParser.deserializeMapper().readValue(
                response.getBody(),
                new TypeReference<List<AccountDto>>() {
                }
        );
        accountsService.insertAllAccounts(accounts);
    }

    @GetMapping("/accounts/{id}")
    @SneakyThrows
    @ApiOperation("Get account out HRM by id")
    public void getAccountsById(@PathVariable("id") Integer userId) {
        String url = builder
                .scheme(HTTP)
                .host(HOST)
                .port(PORT)
                .addPathSegments(API_ACCOUNTS)
                .addPathSegment(userId.toString())
                .toString();
        ResponseEntity<String> response = httpRequestService.createRequest(
                url,
                HttpMethod.GET,
                httpRequestService.requestBody("")
        );
        AccountDto accounts = JacksonParser.deserializeMapper().readValue(
                response.getBody(),
                new TypeReference<AccountDto>() {
                }
        );

        accountsService.insertAccount(accounts);
    }

    @PostMapping(
            value = "/accounts/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Update account in game db")
    public void updateAccount(
            @PathVariable("id") Long userId,
            @RequestBody AccountDto account
    ) {
        accountsService.updateUserData(account);
    }

    @PostConstruct
    @SneakyThrows
    public void initAllAccountsBeforeStartServer() {
        startInitDataBase();
    }

    @Scheduled(cron = "0 0 0 * * *")
    @SneakyThrows
    @ApiOperation("Init update accounts from cron config")
    public void updateAccountsDataScheduled() {
        startInitDataBase();
    }

    @SneakyThrows
    private void startInitDataBase() {
        String url = builder
                .scheme(HTTP)
                .host(HOST)
                .port(PORT)
                .addPathSegments(API_ACCOUNTS).toString();
        ResponseEntity<String> response = httpRequestService.createRequest(
                url,
                HttpMethod.GET,
                httpRequestService.requestBody("")
        );
        List<AccountDto> accounts = JacksonParser.deserializeMapper().readValue(
                response.getBody(),
                new TypeReference<List<AccountDto>>() {
                }
        );
        accountsService.insertAllAccounts(accounts);
    }
}
