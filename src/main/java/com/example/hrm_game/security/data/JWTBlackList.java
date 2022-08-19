package com.example.hrm_game.security.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "blacklist")
public class JWTBlackList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "add_black_list_date")
    private LocalDate addBlackListDate;
}
