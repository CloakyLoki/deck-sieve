package com.cloakyloki.mapper;

import com.cloakyloki.dto.UserReadDto;
import com.cloakyloki.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User user) {
        return new UserReadDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getIsActive()
        );
    }
}
