package com.cloakyloki.config;

import com.cloakyloki.dto.CustomUserDetails;
import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.lang.reflect.Proxy;
import java.time.LocalTime;
import java.util.Set;

import static com.cloakyloki.entity.enumerated.Role.ADMIN;
import static com.cloakyloki.entity.enumerated.Role.USER;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        urlConfig -> urlConfig
                                .antMatchers("/admin/**", "/users/{\\d+}/delete", "/cards/{\\d+}/delete").hasAuthority(ADMIN.getAuthority())
                                .antMatchers("/login", "/index", "/users/registration", "/decks/**", "/cards").permitAll()
                                .antMatchers("/cards/**").hasAnyAuthority("USER", "ADMIN")
                                .antMatchers(HttpMethod.POST, "/users").permitAll()
                                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/js/**/", "/css/**/", "/img/**/").permitAll()
                                .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/index"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/index"))
                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/index")
                        .userInfoEndpoint(userinfo -> userinfo.oidcUserService(oidUserService()))
                );
        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidUserService() {
        return userRequest -> {

            String name = userRequest.getIdToken().getClaim("email");
            if (userService.findByUsername(name).isEmpty()) {
                userService.create(new UserCreateUpdateDto(name, LocalTime.now().toString(), USER, true));
            }
            CustomUserDetails user = userService.loadUserByUsername(name);
            DefaultOidcUser oidcUser = new DefaultOidcUser(user.getAuthorities(), userRequest.getIdToken());

            var userDetailsMethods = new java.util.HashSet<>(Set.of(UserDetails.class.getMethods()));
            userDetailsMethods.addAll(Set.of(CustomUserDetails.class.getMethods()));

            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{CustomUserDetails.class, OidcUser.class},
                    (proxy, method, args) -> userDetailsMethods.contains(method)
                            ? method.invoke(user, args)
                            : method.invoke(oidcUser, args));
        };
    }
}