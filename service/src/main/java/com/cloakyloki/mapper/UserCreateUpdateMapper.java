package com.cloakyloki.mapper;

import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserCreateUpdateMapper implements Mapper<UserCreateUpdateDto, User> {

    @Override
    public User map(UserCreateUpdateDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateUpdateDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    private void copy(UserCreateUpdateDto object, User user) {
        user.setUsername(object.getUsername());
        user.setPassword(object.getPassword());
        user.setRole(object.getRole());
        user.setIsActive(object.getIsActive());
    }
}
