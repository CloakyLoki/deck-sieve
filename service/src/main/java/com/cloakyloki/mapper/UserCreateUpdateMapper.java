package com.cloakyloki.mapper;

import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateUpdateMapper implements Mapper<UserCreateUpdateDto, User> {

    private final PasswordEncoder passwordEncoder;

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
        user.setRole(object.getRole());
        user.setIsActive(object.getIsActive());

        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
    }
}
