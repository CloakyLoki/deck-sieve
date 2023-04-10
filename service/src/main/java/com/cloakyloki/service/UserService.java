package com.cloakyloki.service;

import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.dto.UserReadDto;
import com.cloakyloki.mapper.UserCreateUpdateMapper;
import com.cloakyloki.mapper.UserReadMapper;
import com.cloakyloki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateUpdateMapper userCreateUpdateMapper;

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateUpdateDto user) {
        return Optional.of(user)
                .map(userCreateUpdateMapper::map)
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateUpdateDto userCreateUpdateDto) {
        return userRepository.findById(id)
                .map(userEntity -> userCreateUpdateMapper.map(userCreateUpdateDto, userEntity))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(userEntity -> {
                    userRepository.delete(userEntity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}