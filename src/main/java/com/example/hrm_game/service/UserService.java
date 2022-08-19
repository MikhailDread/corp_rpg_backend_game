package com.example.hrm_game.service;

import com.example.hrm_game.data.dto.game.UserDto;
import com.example.hrm_game.data.entity.equipment.ChestEntity;
import com.example.hrm_game.data.entity.equipment.InventoryEntity;
import com.example.hrm_game.data.entity.equipment.WeaponEntity;
import com.example.hrm_game.data.entity.game.*;
import com.example.hrm_game.repository.*;
import com.example.hrm_game.security.data.JWTBlackList;
import com.example.hrm_game.security.repository.JWTBlackListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private UserAchievementRepository userAchievementRepository;
    @Autowired
    private ChestRepository chestEntityRepository;
    @Autowired
    private WeaponRepository weaponRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTBlackListRepository jwtBlackListRepository;

    /**
     * Функция проливки всех пользователей из HRM.
     * Либо создание и добавление новых пользователей,
     * либо обновление существующих на основе изменения данных в HRM
     *
     * @param users - Список ДТО пользователей, полученных из HRM
     */
    public void insertAllUser(List<UserDto> users) {
        users.forEach(
                user -> {
                    try {
                        //TODO: Задать вопрос: меняются ли в HRM данные
                        // и стоит ли их при проливке апдейтить или только добавлять новых, как сейчас?
                        UserEntity userById = userRepository.findUserEntityById(user.getId());
                        if (userById == null) {
                            List<AchievementEntity> achievementEntity = achievementRepository.findAll();
                            //Создаем юзера, если он отсутствует в БД приложения
                            UserEntity userEntity = new UserEntity();
                            userEntity.setId(user.getId());
                            userEntity.setName(user.getName());
                            userEntity.setDefeat(0);
                            userEntity.setVictories(0);
                            userEntity.setDescription("");
                            userEntity.setCoins(0);
                            userEntity.setBirth(user.getBirth());
                            userEntity.setCity(user.getCity());
                            userEntity.setPhoto(user.getPhoto());
                            userEntity.setGender(user.getGender());
                            userEntity.setAchievementEntity(achievementEntity);

                            LevelEntity level = new LevelEntity();
                            level.setLevel(1);
                            level.setCurrentExperience(0);
                            level.setMaxExperience(100);
                            userEntity.setLevel(level);

                            userRepository.save(userEntity);
                        } else {
                            //Обновляем имя и админа, вдруг поменялись
                            userById.setName(user.getName());
                            userById.setBirth(user.getBirth());
                            userById.setCity(user.getCity());
                            userById.setGender(user.getGender());
                        }
                    } catch (NullPointerException ex) {
                        log.error("Пустой айди у пользователя " + user.getId());
                    }
                }
        );
    }

    /**
     * Функция проливки пользователя из HRM.
     * Либо создание и добавление новых пользователей,
     * либо обновление существующих на основе изменения данных в HRM
     *
     * @param users - Список ДТО пользователей, полученных из HRM
     */
    public void insertUser(UserDto user) {
        try {
            UserEntity userById = userRepository.findUserEntityById(user.getId());
            if (userById == null) {
                //Создаем юзера, если он отсутствует в БД приложения
                UserEntity userEntity = new UserEntity();
                List<AchievementEntity> achievementEntity = achievementRepository.findAll();
                userEntity.setId(user.getId());
                userEntity.setName(user.getName());
                userEntity.setDefeat(0);
                userEntity.setVictories(0);
                userEntity.setDescription("");
                userEntity.setCoins(0);
                userEntity.setBirth(user.getBirth());
                userEntity.setCity(user.getCity());
                userEntity.setPhoto(user.getPhoto());
                userEntity.setGender(user.getGender());

                LevelEntity level = new LevelEntity();
                level.setLevel(1);
                level.setCurrentExperience(0);
                level.setMaxExperience(100);
                userEntity.setLevel(level);

                userRepository.save(userEntity);
            } else {
                //Обновляем имя и админа, вдруг поменялись
                userById.setName(user.getName());
                userById.setBirth(user.getBirth());
                userById.setCity(user.getCity());
                userById.setGender(user.getGender());
            }
        } catch (NullPointerException ex) {
            log.error("Пустой айди у пользователя " + user.getId());
        }
    }

    /**
     * Функция получения пользователя по id
     *
     * @param id - id пользователя
     * @return - сущность пользователя из БД
     */
    public UserEntity getUserById(Long id) {
        return userRepository.findUserEntityById(id);
    }

    /**
     * Функция получения пользователя по логину
     *
     * @param login - логин
     * @return - сущность пользователя из БД
     */
    public UserEntity getUserByLogin(String login) {
        return userRepository.findUserEntityByLogin(login);
    }

    /**
     * Функция обновления данных пользователя
     *
     * @param user - сущность пользователя после редактирования
     */
    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }

    /**
     * Функция поиска пользователя по связке логин+пароль
     *
     * @param login    - логин
     * @param password - пароль
     * @return - сущность пользователя из БД
     */
    public UserEntity findUserByLoginAndPasswordAndMatches(String login, String password) {
        UserEntity userEntity = getUserByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        throw new UsernameNotFoundException("User password and password from request not matched!");
    }
}
