package com.cloakyloki.repository;

import com.cloakyloki.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<User> findByUsername(String username);
}