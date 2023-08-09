package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.exception.KeycloakUserDoesNotExist;
import io.github.lizewskik.susieserver.keycloak.security.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KeycloakService keycloakService;

    @Override
    public String getCurrentLoggedUserUUID() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return jwt.getSubject();
    }

    @Override
    public String getUserUUIDByEmail(String email) {
        return Optional.of(keycloakService.getUserUUIDByEmail(email)).orElseThrow(KeycloakUserDoesNotExist::new);
    }
}
