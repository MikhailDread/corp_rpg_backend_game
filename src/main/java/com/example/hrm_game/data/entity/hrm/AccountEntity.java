package com.example.hrm_game.data.entity.hrm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts")
@RequiredArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Account entity")
public class AccountEntity {
    @Id
    @ApiModelProperty(value = "Account id add out of HRM")
    private Long id;
    @ApiModelProperty(value = "Account name add out HRM")
    private String name;
    @Column(name = "active_admin")
    @ElementCollection
    private List<String> activeAdmin;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project", referencedColumnName = "id")
    private ProjectEntity projectEntity;
}
