package com.example.hrm_game.data.entity.hrm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "testing_types")
@RequiredArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Testing type entity")
public class TestingTypeEntity {
    @Id
    @ApiModelProperty(value = "Testing type id add by HRM")
    private Long id;
    @ApiModelProperty(value = "Testing type role description")
    private String description;
    @ApiModelProperty(value = "Testing type name add out HRM")
    private String name;
}
