package com.example.hrm_game.controller;

import com.example.hrm_game.service.LevelUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api("Controller of gaining experience and levels from users")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful request"),
        @ApiResponse(code = 403, message = "User not authorized"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 503, message = "Service is drop")
})
public class LevelUpRestController {
    @Autowired
    private LevelUpService levelUpService;

    @PostMapping("/user/progress/{id}")
    @ApiOperation("Experience progress and level up")
    public void experienceProgressForUser(
            @PathVariable("id") Long userId,
            @RequestBody Integer experience,
            HttpServletRequest httpServletRequest
    ) {
        levelUpService.experienceProgress(userId, experience);
    }
}
