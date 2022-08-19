package com.example.hrm_game.service;

import com.example.hrm_game.data.dto.game.QuestDto;
import com.example.hrm_game.data.entity.game.QuestEntity;
import com.example.hrm_game.data.entity.game.UsersQuestsEntity;
import com.example.hrm_game.repository.QuestRepository;
import com.example.hrm_game.repository.UserQuestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestService {
    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserQuestsRepository userQuestsRepository;
    @Autowired
    private LevelUpService levelUpService;
    @Autowired
    private CoinService coinService;

    /**
     * Функция получения квеста пользователем
     *
     * @param questId - id квеста
     * @param userId  - id пользователя
     */
    public void addQuestForUser(final Long questId, final Long userId) {
        List<UsersQuestsEntity> usersQuests = getUsersQuests(userId);
        QuestEntity quest = getQuestById(questId);
        Optional<UsersQuestsEntity> targetQuest = getTargetQuest(usersQuests, questId);
        if (targetQuest.isEmpty()) {
            UsersQuestsEntity addNewQuest = new UsersQuestsEntity();
            addNewQuest.setQuests(quest);
            addNewQuest.setUser(userService.getUserById(userId));
            addNewQuest.setProgress(0);
            addNewQuest.setTotal(quest.getTotal());
            saveUserQuest(addNewQuest);
        }
    }

    /**
     * Функция расчета прогресса прохождения квеста
     *
     * @param questId  - id квеста
     * @param userId   - id пользователя
     * @param questDto - ДТО, описывающее критерии текущего квеста
     * @return - условие завершен/не завершен квеста
     */
    public boolean getQuestProgress(final Long questId, final Long userId, final QuestDto questDto) {
        List<UsersQuestsEntity> usersQuests = getUsersQuests(userId);
        UsersQuestsEntity targetQuest = getTargetQuest(usersQuests, questId).get();
        if (!targetQuest.isCompleted()) {
            targetQuest.setProgress(targetQuest.getProgress() + questDto.getProgress());
            if (targetQuest.getProgress() >= targetQuest.getTotal()) {
                targetQuest.setCompleted(true);
                saveUserQuest(targetQuest);
                return true;
            } else {
                saveUserQuest(targetQuest);
                return false;
            }
        }
        return false;
    }

    /**
     * Функция получения награды за квест
     *
     * @param questId  - id квеста
     * @param userId   - id пользователя
     * @param questDto - ДТО, описывающее критерии текущего квеста
     */
    public void addAwards(final Long questId, final Long userId, final QuestDto questDto) {
        List<UsersQuestsEntity> usersQuests = getUsersQuests(userId);
        UsersQuestsEntity targetQuest = getTargetQuest(usersQuests, questId).get();
        if (targetQuest.isCompleted()) {
            if (questDto.getCoins() != null) {
                coinService.addCoinsForUser(userId, questDto.getCoins());
            }
            levelUpService.experienceProgress(userId, questDto.getExperience());

            userService.getUserById(userId);
        }
    }

    /**
     * Функция валидации наличия квеста у пользователя
     * (применяется при сборе ресурсов, убийстве мобов для идентификации получения прогресса)
     *
     * @param questId - id квеста
     * @param userId  - id пользователя
     * @return - условие имеется/не имеется квест у пользователя
     */
    public boolean validQuestAtUser(final Long questId, final Long userId) {
        List<UsersQuestsEntity> usersQuests = getUsersQuests(userId);
        Optional<UsersQuestsEntity> targetQuest = getTargetQuest(usersQuests, questId);
        return targetQuest.isPresent();
    }

    /**
     * Функция получения всех квестов у пользователя
     *
     * @param userId - id пользователя
     * @return - список квестом у текущего пользователя
     */
    public List<UsersQuestsEntity> getUsersQuests(final Long userId) {
        return userQuestsRepository.findUsersQuestEntityByUserId(userId);
    }

    /**
     * Функция получения у пользователя конкретного квеста
     *
     * @param usersQuests - список квестов пользователя
     * @param questId     - id искомого квеста
     * @return - искомый квест
     */
    public Optional<UsersQuestsEntity> getTargetQuest(final List<UsersQuestsEntity> usersQuests, final Long questId) {
        return usersQuests
                .stream()
                .filter(usr ->
                        usr.getQuests().getId().equals(questId)
                )
                .findAny();
    }

    /**
     * Функция сохранения квеста для пользователя
     *
     * @param targetQuest - текущий квест
     */
    private void saveUserQuest(UsersQuestsEntity targetQuest) {
        userQuestsRepository.save(targetQuest);
    }

    /**
     * Функция получения квеста по id
     *
     * @param questId - id квеста
     * @return - сущность квеста из БД
     */
    public QuestEntity getQuestById(final Long questId) {
        return questRepository.findQuestEntityById(questId);
    }
}
