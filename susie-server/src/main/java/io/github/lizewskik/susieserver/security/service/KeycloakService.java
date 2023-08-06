package io.github.lizewskik.susieserver.security.service;

import dev.mccue.guava.base.Joiner;
import io.github.lizewskik.susieserver.security.config.KeycloakConfig;
import io.github.lizewskik.susieserver.security.dto.response.RegistrationResponse;
import io.github.lizewskik.susieserver.security.dto.UserDTO;
import io.github.lizewskik.susieserver.security.dto.request.SignInRequest;
import io.github.lizewskik.susieserver.security.utils.builder.UserRepresentationBuilder;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.*;

import static io.github.lizewskik.susieserver.security.dictionary.KeycloakDictionary.*;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private static final String ID_FOR_CLIENT_ID_NOT_FOUND_MSG = "Entity ID for Client ID not found";
    private static final String ROLE_DOES_NOT_EXIST = "Corresponding client role does not exist: ";

    private final KeycloakConfig keycloakConfig;

    public Map.Entry<Integer, RegistrationResponse> register(UserDTO user) {

        UserRepresentation userRepresentation = UserRepresentationBuilder.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .enabled(Boolean.TRUE)
                .emailVerified(Boolean.TRUE)
                .build();

        Response response = keycloakConfig.getInstance()
                .realm(keycloakConfig.getRealm())
                .users()
                .create(userRepresentation);

        RegistrationResponse info = RegistrationResponse.builder().result(response.getStatusInfo().getReasonPhrase()).success(Boolean.FALSE).build();
        if (response.getStatus() == HttpStatus.SC_CREATED) {
            info.setSuccess(Boolean.TRUE);
            assignRoleToUser(getUserUUIDFromResponse(response.getLocation().toString()));
        }

        return new AbstractMap.SimpleEntry<>(response.getStatus(), info);
    }

    public AccessTokenResponse signIn(SignInRequest credentials) {
        return AuthzClient
                .create(keycloakConfig.getConfiguration())
                .obtainAccessToken(credentials.getUsername(), credentials.getPassword());
    }

    private List<RoleRepresentation> getClientRolesByName(List<String> clientRoles) {
        String entityID = getIDForClientID(keycloakConfig.getClientId());
        RealmResource realmResource = keycloakConfig.getInstance().realm(keycloakConfig.getRealm());
        List<RoleRepresentation> roles = new ArrayList<>();
        clientRoles.forEach(role -> roles.add(Optional.of(realmResource.clients().get(entityID).roles().get(role).toRepresentation())
                .orElseThrow(() -> new RuntimeException(ROLE_DOES_NOT_EXIST.concat(role)))));
        return roles;
    }

    private void assignRoleToUser(String userUUID) {
        this.keycloakConfig
                .getInstance()
                .realm(keycloakConfig.getRealm())
                .users()
                .get(userUUID)
                .roles()
                .clientLevel(getIDForClientID(keycloakConfig.getClientId()))
                .add(getClientRolesByName(List.of(KEYCLOAK_CLIENT_ROLE_USER)));
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
        return location.replaceAll(buildLocationURI(), EMPTY_WORD);
    }

    private String buildLocationURI() {
        return Joiner
                .on(URI_SEPARATOR)
                .skipNulls()
                .join(keycloakConfig.getServerUrl(),
                        KEYCLOAK_URI_ADMIN_PARAMETER,
                        KEYCLOAK_URI_REALMS_PARAMETER,
                        keycloakConfig.getRealm(),
                        KEYCLOAK_URI_USERS_PARAMETER)
                .concat(URI_SEPARATOR);
    }
}
