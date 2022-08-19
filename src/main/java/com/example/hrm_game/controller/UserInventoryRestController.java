package com.example.hrm_game.controller;

import com.example.hrm_game.data.dto.game.ItemDto;
import com.example.hrm_game.data.dto.game.QuestDto;
import com.example.hrm_game.service.UserInventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api("User backpack manipulation controller. Put, delete and add items in user inventory")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful request"),
        @ApiResponse(code = 403, message = "User not authorized"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 503, message = "Service is drop")
})
public class UserInventoryRestController {
    @Autowired
    private UserInventoryService userInventoryService;

    @PostMapping(
            value = "/inventory/add_item/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Add item in inventory by quest/event/shop/craft")
    public void addItemInInventory(
            @PathVariable(name = "id") Long userId,
            @RequestBody QuestDto questDto,
            HttpServletRequest httpServletRequest
    ) {
        userInventoryService.addItemInUserInventory(userId, questDto);
    }

    @GetMapping(
            value = "/inventory/put_on_item/user/{idUser}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Put item from inventory to user")
    public void putItemToUser(
            @PathVariable("idUser") Long userId,
            @RequestBody ItemDto item,
            HttpServletRequest httpServletRequest
    ) {
        userInventoryService.putItemToUserById(userId, item);
    }

    @PostMapping(
            value = "/inventory/delete_item/user/{idUser}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Delete item from inventory")
    public void deleteItemFromInventory(
            @PathVariable("idUser") Long userId,
            @RequestBody ItemDto item,
            HttpServletRequest httpServletRequest
    ) {
        userInventoryService.deleteItemFromUserInventory(userId, item);
    }
}
