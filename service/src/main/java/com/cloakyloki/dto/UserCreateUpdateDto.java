package com.cloakyloki.dto;

import com.cloakyloki.entity.enumerated.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserCreateUpdateDto {

    String nickname;
    String password;
    Role role;
    Boolean isActive;
}
