package com.example.hrm_game.service;

import com.example.hrm_game.data.dto.hrm.AccountDto;
import com.example.hrm_game.data.entity.hrm.AccountEntity;
import com.example.hrm_game.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Функция добавления аккаунтов из HRM в БД приложения
     *
     * @param accounts - список выгруженных из HRM аккаунтов
     */
    public void insertAllAccounts(List<AccountDto> accounts) {
        accounts.forEach(
                account -> {
                    try {
                        //TODO: Задать вопрос: меняются ли в HRM данные
                        // и стоит ли их при проливке апдейтить или только добавлять новых, как сейчас?
                        AccountEntity accountEntityById = accountRepository.findAccountEntityById(account.getId());
                        if (accountEntityById == null) {
                            //Создаем аккаунт, если он отсутствует в БД приложения
                            AccountEntity accountEntity = new AccountEntity();
                            accountEntity.setId(account.getId());
                            accountEntity.setName(account.getName());
                            accountEntity.setActiveAdmin(account.getActiveAdmin());
                            accountRepository.save(accountEntity);
                        } else {
                            //Обновляем имя и админа, вдруг поменялись
                            accountEntityById.setName(account.getName());
                            accountEntityById.setActiveAdmin(account.getActiveAdmin());
                        }
                    } catch (NullPointerException ex) {
                        log.error("Пустой айди у пользователя " + account.getId());
                    }
                }
        );
    }

    /**
     * Функция создания и добавления нового аккаунта в БД
     *
     * @param account - ДТО аккаунта
     */
    public void insertAccount(AccountDto account) {
        try {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setId(account.getId());
            accountEntity.setName(account.getName());
            accountEntity.setActiveAdmin(account.getActiveAdmin());
            accountRepository.save(accountEntity);
        } catch (NullPointerException ex) {
            log.error("Пустой айди у пользователя " + account.getId());
        }
    }

    /**
     * Функция получения аккаунт из внутреннего сервиса по id
     *
     * @param id - id аккаунта
     */
    public AccountEntity getAccountFromId(Long id) {
        return accountRepository.findAccountEntityById(id);
    }

    /**
     * Функция обновления аккаунта в БД приложения в случае изменения его данных
     *
     * @param account - ДТО аккаунта
     */
    public void updateUserData(AccountDto account) {
        //TODO: мб как-то убрать или подумать что с ним делать
        AccountEntity accountEntity = accountRepository.findAccountEntityById(account.getId());
        accountEntity.setName(account.getName());
        accountEntity.setActiveAdmin(account.getActiveAdmin());
        accountRepository.save(accountEntity);
    }
}
