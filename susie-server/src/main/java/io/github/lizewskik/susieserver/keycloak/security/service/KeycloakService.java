package io.github.lizewskik.susieserver.keycloak.security.service;

import dev.mccue.guava.base.Joiner;
import io.github.lizewskik.susieserver.keycloak.security.config.KeycloakConfig;
import io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary;
import io.github.lizewskik.susieserver.keycloak.security.dto.SimpleRoleRepresentation;
import io.github.lizewskik.susieserver.keycloak.security.dto.request.RegistrationRequest;
import io.github.lizewskik.susieserver.keycloak.security.dto.request.SignInRequest;
import io.github.lizewskik.susieserver.keycloak.security.dto.response.AccessTokenExtendedResponse;
import io.github.lizewskik.susieserver.keycloak.security.dto.response.RegistrationResponse;
import io.github.lizewskik.susieserver.keycloak.security.utils.builder.UserRepresentationBuilder;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.KEYCLOAK_CLIENT_ROLE_DEVELOPER;
import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.KEYCLOAK_CLIENT_ROLE_SCRUM_MASTER;
import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.KEYCLOAK_CLIENT_ROLE_USER;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private static final String ID_FOR_CLIENT_ID_NOT_FOUND_MSG = "Entity ID for Client ID not found";
    private static final String ROLE_DOES_NOT_EXIST = "Corresponding client role does not exist: ";

    private final KeycloakConfig keycloakConfig;

    public Map.Entry<Integer, RegistrationResponse> register(RegistrationRequest user) {

        UserRepresentation userRepresentation = UserRepresentationBuilder.builder()
                .username(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .email(user.getEmail())
                .emailVerified(Boolean.TRUE)
                .enabled(Boolean.TRUE)
                .build();

        Response response = keycloakConfig.getInstance()
                .realm(keycloakConfig.getRealm())
                .users()
                .create(userRepresentation);

        RegistrationResponse info = RegistrationResponse.builder().result(response.getStatusInfo().getReasonPhrase()).success(Boolean.FALSE).build();
        String userUUID = getUserUUIDFromResponse(response.getLocation().toString());

        if (response.getStatus() == HttpStatus.SC_CREATED) {
            info.setSuccess(Boolean.TRUE);
            assignRoleToUser(userUUID, KEYCLOAK_CLIENT_ROLE_USER);
            if (user.getIsScrumMaster()) {
                assignRoleToUser(userUUID, KEYCLOAK_CLIENT_ROLE_SCRUM_MASTER);
            } else {
                assignRoleToUser(userUUID, KEYCLOAK_CLIENT_ROLE_DEVELOPER);
            }
        }

        return new AbstractMap.SimpleEntry<>(response.getStatus(), info);
    }

    public AccessTokenExtendedResponse signIn(SignInRequest credentials) {

        AccessTokenResponse tokenInfo = AuthzClient
                .create(keycloakConfig.getConfiguration())
                .obtainAccessToken(credentials.getUsername(), credentials.getPassword());

        AccessTokenExtendedResponse response = new AccessTokenExtendedResponse(tokenInfo);

        UserRepresentation userRepresentation = keycloakConfig.getInstance()
                .realm(keycloakConfig.getRealm()).users()
                .searchByUsername(credentials.getUsername(), Boolean.TRUE)
                .get(0);

        List<SimpleRoleRepresentation> userRoles = this.keycloakConfig
                .getInstance()
                .realm(keycloakConfig.getRealm())
                .users()
                .get(userRepresentation.getId())
                .roles().clientLevel(getIDForClientID(keycloakConfig.getClientId()))
                .listAll().stream().map(role -> SimpleRoleRepresentation.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .build())
                .toList();

        response.setUserRoles(userRoles);
        return response;
    }

    public UserRepresentation getUserUUIDByEmail(String email) {

        return keycloakConfig.getInstance()
                .realm(keycloakConfig.getRealm())
                .users()
                .searchByUsername(email, Boolean.TRUE)
                .get(0);
    }

    public UserRepresentation getUserUUIDByUUID(String uuid) {

        return keycloakConfig.getInstance()
                .realm(keycloakConfig.getRealm())
                .users()
                .get(uuid)
                .toRepresentation();
    }

    private List<RoleRepresentation> getClientRolesByName(List<String> clientRoles) {
        String entityID = getIDForClientID(keycloakConfig.getClientId());
        RealmResource realmResource = keycloakConfig.getInstance().realm(keycloakConfig.getRealm());
        List<RoleRepresentation> roles = new ArrayList<>();
        clientRoles.forEach(role -> roles.add(Optional.of(realmResource.clients().get(entityID).roles().get(role).toRepresentation())
                .orElseThrow(() -> new RuntimeException(ROLE_DOES_NOT_EXIST.concat(role)))));
        return roles;
    }

    private void assignRoleToUser(String userUUID, String clientRole) {
        this.keycloakConfig
                .getInstance()
                .realm(keycloakConfig.getRealm())
                .users()
                .get(userUUID)
                .roles()
                .clientLevel(getIDForClientID(keycloakConfig.getClientId()))
                .add(getClientRolesByName(List.of(clientRole)));
    }

    private String getIDForClientID(String clientID) {
        return Optional.of(
                keycloakConfig.getInstance()
                        .realm(keycloakConfig.getRealm())
                        .clients().findByClientId(clientID)
                        .get(0)
                        .getId()
        ).orElseThrow(() -> new RuntimeException(ID_FOR_CLIENT_ID_NOT_FOUND_MSG));
    }

    private String getUserUUIDFromResponse(String location) {
        return location.replaceAll(buildLocationURI(), KeycloakDictionary.EMPTY_WORD);
    }

    private String buildLocationURI() {
        return Joiner
                .on(KeycloakDictionary.URI_SEPARATOR)
                .skipNulls()
                .join(keycloakConfig.getServerUrl(),
                        KeycloakDictionary.KEYCLOAK_URI_ADMIN_PARAMETER,
                        KeycloakDictionary.KEYCLOAK_URI_REALMS_PARAMETER,
                        keycloakConfig.getRealm(),
                        KeycloakDictionary.KEYCLOAK_URI_USERS_PARAMETER)
                .concat(KeycloakDictionary.URI_SEPARATOR);
    }
}
