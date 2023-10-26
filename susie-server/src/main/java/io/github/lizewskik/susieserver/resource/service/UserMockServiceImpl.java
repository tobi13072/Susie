package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.dto.UserDTO;
import org.springframework.stereotype.Service;

import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.EMPTY_USER_EMAIL;
import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.EMPTY_USER_FIRSTNAME;
import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.EMPTY_USER_LASTNAME;
import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.EMPTY_USER_UUID;

@Service
public class UserMockServiceImpl implements UserMockService {
    @Override
    public UserDTO mockEmptyUser() {
        return UserDTO.builder()
                .uuid(EMPTY_USER_UUID)
                .email(EMPTY_USER_EMAIL)
                .firstName(EMPTY_USER_FIRSTNAME)
                .lastName(EMPTY_USER_LASTNAME)
                .build();
    }
}
