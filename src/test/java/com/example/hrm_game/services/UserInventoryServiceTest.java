package com.example.hrm_game.services;

import com.example.hrm_game.data.dto.game.ItemDto;
import com.example.hrm_game.data.dto.game.QuestDto;
import com.example.hrm_game.data.entity.equipment.DefenderEntity;
import com.example.hrm_game.data.entity.equipment.InventoryEntity;
import com.example.hrm_game.data.entity.game.UserEntity;
import com.example.hrm_game.repository.InventoryRepository;
import com.example.hrm_game.repository.UserRepository;
import com.example.hrm_game.service.ItemService;
import com.example.hrm_game.service.UserInventoryService;
import com.example.hrm_game.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static com.example.hrm_game.data.dto.game.ItemEnum.CHEST;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserInventoryServiceTest {
    @Mock
    private InventoryRepository inventoryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemService itemService;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserInventoryService userInventoryService;
    private UserEntity user;
    private InventoryEntity inventory;
    private ItemDto item;
    private QuestDto questDto;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(45L);
        user.setLogin("mokito_test");
        user.setPassword("1234567");
        user.setCoins(100);
        user.setName("mokito_test");

        inventory = new InventoryEntity();
        inventory.setUser(user);

        item = new ItemDto();
        item.setId(1L);
        item.setItemEnum(CHEST);

        questDto = new QuestDto();
        questDto.setAwards(Collections.singletonList(item));
    }

    @AfterEach
    void tearDown(){
        user = null;
        inventory = null;
        item = null;
        questDto = null;
    }

    @Test
    void getUserInventoryTest(){
        userInventoryService.getUserInventory(user);
        verify(inventoryRepository, Mockito.times(1)).findInventoryEntityByUser(user);
    }

    @Test
    void saveUserInventoryTest(){
        userInventoryService.saveUserInventory(inventory);
        verify(inventoryRepository, Mockito.times(1)).save(inventory);
    }

    @Test
    void addItemInUserInventoryTest(){
//        UserInventoryService spy = spy(UserInventoryService.class);
//        spy.getUserInventory()
        ArgumentCaptor<InventoryEntity> userInventoryServiceArgumentCaptor =
                ArgumentCaptor.forClass(InventoryEntity.class);
        when(inventoryRepository.save(userInventoryServiceArgumentCaptor.capture())).thenReturn(inventory);
        ArgumentCaptor<Long> userCaptor =
                ArgumentCaptor.forClass(Long.class);
        when(userService.getUserById(userCaptor.capture())).thenReturn(user);
        userInventoryService.getUserInventory(user);
        userInventoryService.addItemInUserInventory(user.getId(), questDto);

        verify(inventoryRepository, Mockito.times(1)).save(inventory);
    }
}
