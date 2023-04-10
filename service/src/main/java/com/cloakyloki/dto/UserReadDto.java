package com.cloakyloki.dto;

import com.cloakyloki.entity.enumerated.Role;
import lombok.Value;

@Value
public class UserReadDto {

    Long id;
    String nickname;
    String password;
    Role role;
    Boolean isActive;
}
