package com.cloakyloki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.cloakyloki.entity.enumerated.Role.ADMIN;
import static com.cloakyloki.entity.enumerated.Role.USER;

@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .csrf().disable()
                .authorizeHttpRequests(
                        urlConfig -> urlConfig
                                .antMatchers("/login", "/index", "/cards/**", "/users/registration", "/swagger-ui/**", "/v3/api-docs/**", "/js/**/", "/css/**/", "/img/**/").permitAll()
                                .antMatchers(HttpMethod.POST, "/users").permitAll()
                                .antMatchers("/admin/**", "/users/{\\d+}/delete", "/cards/{\\d+}/delete").hasAuthority(ADMIN.getAuthority())
                                .antMatchers("/cards/**").hasAnyAuthority(ADMIN.getAuthority(), USER.getAuthority())
                                .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/index"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
