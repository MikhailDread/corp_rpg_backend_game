package com.example.hrm_game.services;

import com.example.hrm_game.data.dto.game.ItemDto;
import com.example.hrm_game.data.dto.game.QuestDto;
import com.example.hrm_game.data.entity.equipment.InventoryEntity;
import com.example.hrm_game.data.entity.game.UserEntity;
import com.example.hrm_game.repository.*;
import com.example.hrm_game.service.ItemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.hrm_game.data.dto.game.ItemEnum.CHEST;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @InjectMocks
    private ItemService itemService;
    @Mock
    private ChestRepository chestEntityRepository;
    @Mock
    private DefenderRepository defenderRepository;
    @Mock
    private HelmetRepository helmetRepository;
    @Mock
    private LeggingsRepository leggingsRepository;
    @Mock
    private WeaponRepository weaponRepository;

    private UserEntity user;
    private InventoryEntity inventory;
    private ItemDto item;
    private QuestDto questDto;

    @BeforeEach
    void setUp() {
        item = new ItemDto();
        item.setId(1L);
        item.setItemEnum(CHEST);
    }

    @AfterEach
    void tearDown(){
        item = null;
    }

    @Test
    void getChestByIdTest(){
        itemService.getChestById(item);
        verify(chestEntityRepository, Mockito.times(1)).findChestEntityById(item.getId());
    }

    @Test
    void getDefenderByIdTest(){
        itemService.getDefenderById(item);
        verify(defenderRepository, Mockito.times(1)).findDefenderEntityById(item.getId());
    }

    @Test
    void getHelmetByIdTest(){
        itemService.getHelmetById(item);
        verify(helmetRepository, Mockito.times(1)).findHelmetEntityById(item.getId());
    }

    @Test
    void getLeggingsByIdTest(){
        itemService.getLeggingsById(item);
        verify(leggingsRepository, Mockito.times(1)).findLeggingsEntityById(item.getId());
    }

    @Test
    void getWeaponByIdTest(){
        itemService.getWeaponById(item);
        verify(weaponRepository, Mockito.times(1)).findWeaponEntityById(item.getId());
    }
}
