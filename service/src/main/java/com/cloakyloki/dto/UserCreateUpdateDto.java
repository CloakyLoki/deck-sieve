package com.cloakyloki.dto;

import com.cloakyloki.entity.enumerated.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserCreateUpdateDto {

    String username;
    String password;
    Role role;
    Boolean isActive;
}
