package io.github.lizewskik.susieserver.keycloak.security.service.helper;

import io.github.lizewskik.susieserver.resource.dto.UserDTO;

import java.util.Set;

public interface KeycloakHelperService {

    void saveAccountDataDeletion();
    UserDTO getCurrentLoggedInUser();
    String getCurrentLoggedInUserUUID();
    Set<String> getAllProjectUsersUUIDs(Integer projectID);
}
