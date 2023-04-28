package com.cloakyloki.dto;

import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.validation.CreateAction;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@FieldNameConstants
public class UserCreateUpdateDto {

    @Size(min = 3, max = 20)
    String username;

    @NotBlank(groups = CreateAction.class)
    String password;
    Role role;
    Boolean isActive;
}
