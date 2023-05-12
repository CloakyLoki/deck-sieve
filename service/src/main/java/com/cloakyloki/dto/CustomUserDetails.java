package com.cloakyloki.dto;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {

    Long getId();

    String getUsername();
}
