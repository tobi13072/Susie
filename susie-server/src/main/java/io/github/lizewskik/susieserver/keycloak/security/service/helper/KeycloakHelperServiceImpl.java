package io.github.lizewskik.susieserver.keycloak.security.service.helper;

import io.github.lizewskik.susieserver.resource.dto.UserDTO;
import io.github.lizewskik.susieserver.resource.service.ProjectService;
import io.github.lizewskik.susieserver.resource.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.SM_ACCOUNT_DELETION_FAILED;

@Service
@RequiredArgsConstructor
public class KeycloakHelperServiceImpl implements KeycloakHelperService {

    private final UserService userService;
    private final ProjectService projectService;

    @Override
    public void saveAccountDataDeletion() {

        String currentLoggedInUserUUID = userService.getCurrentLoggedUser().getUuid();
        verifyAccountDeletionAbility(currentLoggedInUserUUID);
        projectService.deleteUserFromAllProjects(currentLoggedInUserUUID);
    }

    @Override
    public UserDTO getCurrentLoggedInUser() {
        return userService.getCurrentLoggedUser();
    }

    @Override
    public String getCurrentLoggedInUserUUID() {
        return userService.getCurrentLoggedUser().getUuid();
    }

    @Override
    public Set<String> getAllProjectUsersUUIDs(Integer projectID) {
        return userService.getAllProjectUsersUUIDs(projectID);
    }

    private void verifyAccountDeletionAbility(String userUUID) {

        if (userService.isAnyProjectOwner(userUUID)) {
            throw new RuntimeException(SM_ACCOUNT_DELETION_FAILED);
        }
    }
}
