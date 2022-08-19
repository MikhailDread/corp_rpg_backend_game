package com.example.hrm_game.data.entity.hrm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "project")
@RequiredArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Project entity")
public class ProjectEntity {
    @Id
    @ApiModelProperty(value = "Project id add by HRM")
    private Long id;
    @ApiModelProperty(value = "Project name add out HRM")
    private String name;
    @OneToOne(mappedBy = "projectEntity", orphanRemoval = true)
    private AccountEntity account;
}
