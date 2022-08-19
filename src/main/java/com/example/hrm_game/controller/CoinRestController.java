package com.example.hrm_game.controller;

import com.example.hrm_game.service.CoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api("Coins added and write-of controller in game and external store")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful request"),
        @ApiResponse(code = 403, message = "User not authorized"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 503, message = "Service is drop")
})
public class CoinRestController {
    @Autowired
    private CoinService coinService;

    @PostMapping(
            value = "/add/coins/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Add coins in user account")
    public void addCoin(
            @PathVariable("id") Long userId,
            @RequestBody Integer coins,
            HttpServletRequest httpServletRequest
    ){
        coinService.addCoinsForUser(userId, coins);
    }

    @PostMapping(
            value = "/writeoff/coins/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Write-off coins out user account")
    public void writeOffCoins(
            @PathVariable("id") Long userId,
            @RequestBody Integer coins,
            HttpServletRequest httpServletRequest
    ){
        coinService.writeOffCoinsFromUser(userId, coins);
    }
}
