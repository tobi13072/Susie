package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.exception.KeycloakUserDoesNotExist;
import io.github.lizewskik.susieserver.keycloak.security.service.KeycloakService;
import io.github.lizewskik.susieserver.resource.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.KEYCLOAK_FIRSTNAME_TOKEN_CLAIM;
import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.KEYCLOAK_LASTNAME_TOKEN_CLAIM;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KeycloakService keycloakService;

    @Override
    public UserDTO getCurrentLoggedUser() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return UserDTO.builder()
                .uuid(jwt.getSubject())
                .email(email)
                .firstName(jwt.getClaimAsString(KEYCLOAK_FIRSTNAME_TOKEN_CLAIM))
                .lastName(jwt.getClaimAsString(KEYCLOAK_LASTNAME_TOKEN_CLAIM))
                .build();
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        UserRepresentation userRepresentation = Optional.of(keycloakService.getUserUUIDByEmail(email)).orElseThrow(KeycloakUserDoesNotExist::new);
        return UserDTO.builder()
                .uuid(userRepresentation.getId())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .build();
    }

    @Override
    public UserDTO getUserByUUID(String uuid) {
        UserRepresentation userRepresentation = Optional.of(keycloakService.getUserUUIDByUUID(uuid)).orElseThrow(KeycloakUserDoesNotExist::new);
        return UserDTO.builder()
                .uuid(uuid)
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .build();
    }
}
