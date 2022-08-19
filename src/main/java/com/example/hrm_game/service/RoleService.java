package com.example.hrm_game.service;

import com.example.hrm_game.data.entity.game.RoleEntity;
import com.example.hrm_game.security.repository.RoleEntityRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

@Service
public class RoleService {
    @Autowired
    private RoleEntityRepository roleEntityRepository;

    /**
     * Функция получения роли пользователя
     *
     * @param roleName - постфикс роли
     * @return - сущность роли, полученная из БД
     */
    @SneakyThrows
    public RoleEntity getRole(String roleName) {
        RoleEntity role = roleEntityRepository.findRoleEntityByRoleName(roleName);
        if (role == null) {
            throw new RoleNotFoundException("Role not found");
        }
        return roleEntityRepository.findRoleEntityByRoleName(roleName);
    }

    /**
     * Функция сохранения роли
     *
     * @param role - сущность роли
     */
    public void saveUserRole(RoleEntity role) {
        roleEntityRepository.save(role);
    }
}
