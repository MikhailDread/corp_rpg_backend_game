package com.example.hrm_game.controller;

import com.example.hrm_game.data.dto.game.QuestDto;
import com.example.hrm_game.data.entity.equipment.ChestEntity;
import com.example.hrm_game.data.entity.equipment.InventoryEntity;
import com.example.hrm_game.repository.InventoryRepository;
import com.example.hrm_game.service.QuestService;
import com.example.hrm_game.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api("Quest manager controller. Add, validate and progress quest")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful request"),
        @ApiResponse(code = 403, message = "User not authorized"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 503, message = "Service is drop")
})
@Slf4j
public class QuestRestController {
    @Autowired
    private QuestService questService;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/quest/take/{id}")
    @ApiOperation("Take quest for user")
    public void takeQuestForUser(
            @PathVariable(name = "id") Long userId,
            @RequestBody Long questId,
            HttpServletRequest httpServletRequest
    ) {
        questService.addQuestForUser(questId, userId);
    }

    @PostMapping(
            value = "/quest/progress/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Progress conditions for obtaining an quest")
    public Mono<ResponseEntity<InventoryEntity>> progressQuest(
            @PathVariable(name = "id") Long userId,
            @RequestBody QuestDto questDto,
            HttpServletRequest httpServletRequest
    ) {
        boolean isProgressCompleted =
                questService.getQuestProgress(questDto.getId(), userId, questDto);
        if (isProgressCompleted) {
            return Mono.just(new HttpHeaders())
                    .doOnNext(header -> header.add("Location", "/quest/completed/" + userId))
                    .map(header -> new ResponseEntity<>(header, HttpStatus.TEMPORARY_REDIRECT));
        }
        return Mono
                .just(new HttpHeaders())
                .map(header -> new ResponseEntity<>(header, HttpStatus.OK));
    }

    @PostMapping(
            value = "/quest/completed/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @SneakyThrows
    @ApiOperation("Complete quest after progress conditions quest")
    public Mono<ResponseEntity<ChestEntity>> completedQuest(
            @PathVariable(name = "id") Long userId,
            @RequestBody QuestDto questDto,
            HttpServletRequest httpServletRequest
    ) {
        questService.addAwards(questDto.getId(), userId, questDto);

        boolean isItemInAwards = questDto.getAwards().size() > 0 && questDto.getAwards() != null;
        if (isItemInAwards) {
            InventoryEntity inventoryEntityByUser =
                    inventoryRepository.findInventoryEntityByUser(userService.getUserById(userId));
            return Mono.just(new HttpHeaders())
                    .doOnNext(header ->
                            header.add("Location", "/inventory/add_item/" + inventoryEntityByUser.getId()))
                    .map(header ->
                            new ResponseEntity<>(header, HttpStatus.TEMPORARY_REDIRECT));
        }
        return Mono
                .just(new HttpHeaders())
                .map(header -> new ResponseEntity<>(header, HttpStatus.OK));
    }

    @PostMapping(
            value = "/quest/check-quest/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Checking if the user has the quest to initiate progress")
    public Mono<ResponseEntity<InventoryEntity>> validateQuest(
            @PathVariable(name = "id") Long userId,
            @RequestBody QuestDto questDto,
            HttpServletRequest httpServletRequest
    ) {
        boolean isValid =
                questService.validQuestAtUser(questDto.getId(), userId);
        if (isValid) {

            return Mono.just(new HttpHeaders())
                    .doOnNext(header -> header.add("Location", "/quest/progress/" + userId))
                    .map(header -> new ResponseEntity<>(header, HttpStatus.TEMPORARY_REDIRECT));
        }
        return Mono
                .just(new HttpHeaders())
                .map(header -> new ResponseEntity<>(header, HttpStatus.OK));
    }
}
