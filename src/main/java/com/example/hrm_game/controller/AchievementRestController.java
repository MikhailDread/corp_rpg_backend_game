package com.example.hrm_game.controller;

import com.example.hrm_game.data.dto.game.AchievementDto;
import com.example.hrm_game.service.AchievementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api("User achievement progress controller")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful request"),
        @ApiResponse(code = 403, message = "User not authorized"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 503, message = "Service is drop")
})
public class AchievementRestController {
    @Autowired
    private AchievementService achievementService;

    @PostMapping(
            value = "/achievement/add/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Adding achievement after complete progress")
    public void addAchievement(
            @PathVariable("id") Long userId,
            @RequestBody AchievementDto achievement,
            HttpServletRequest httpServletRequest
    ) {
        achievementService.addAchievementForUser(userId, achievement);
    }

    @PostMapping(value = "/achievement/progress/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @SneakyThrows
    @ApiOperation("Progress conditions for obtaining an achievement")
    public void progressAchievement(
            @PathVariable("id") Long userId,
            @RequestBody AchievementDto achievement,
            HttpServletResponse response,
            HttpServletRequest httpServletRequest
    ) {
        boolean isCompleted = achievementService.progressAchievementAtUser(userId, achievement);
        if (isCompleted) {
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            response.setHeader("Location", "/achievement/add/" + userId);
        }
    }
}
