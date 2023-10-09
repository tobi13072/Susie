package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.keycloak.security.service.KeycloakService;
import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.dto.UserDTO;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.KEYCLOAK_USER_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.PROJECT_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.KEYCLOAK_FIRSTNAME_TOKEN_CLAIM;
import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.KEYCLOAK_LASTNAME_TOKEN_CLAIM;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KeycloakService keycloakService;
    private final ProjectRepository projectRepository;

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
        UserRepresentation userRepresentation = Optional.of(keycloakService.getUserUUIDByEmail(email))
                .orElseThrow(() -> new RuntimeException(KEYCLOAK_USER_DOES_NOT_EXISTS));
        return UserDTO.builder()
                .uuid(userRepresentation.getId())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .build();
    }

    @Override
    public UserDTO getUserByUUID(String uuid) {
        UserRepresentation userRepresentation = Optional.of(keycloakService.getUserUUIDByUUID(uuid))
                .orElseThrow(() -> new RuntimeException(KEYCLOAK_USER_DOES_NOT_EXISTS));
        return UserDTO.builder()
                .uuid(uuid)
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .build();
    }

    @Override
    public boolean isProjectOwner(Integer projectID) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String currentLoggedUserUUID = jwt.getSubject();
        Project project = projectRepository.findById(projectID).orElseThrow(RuntimeException::new);
        String projectOwnerUUID = project.getProjectOwner();
        return projectOwnerUUID.equals(currentLoggedUserUUID);
    }

    @Override
    public Set<String> getAllProjectUsersUUIDs(Integer projectID) {

        Project project = projectRepository.findById(projectID)
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));

        return project.getUserIDs();
    }
}
