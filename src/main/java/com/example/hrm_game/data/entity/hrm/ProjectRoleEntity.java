package com.example.hrm_game.data.entity.hrm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "project_role")
@RequiredArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Project role entity")
public class ProjectRoleEntity {
    @Id
    @ApiModelProperty(value = "Project role id add by HRM")
    private Long id;
    @ApiModelProperty(value = "Project role name add out HRM")
    private String name;
    @JsonProperty("is_lead")
    @Column(name = "is_lead")
    @ApiModelProperty(value = "Project role lead status at user")
    private Boolean isLead;
    @ApiModelProperty(value = "Project role description")
    private String description;
}
