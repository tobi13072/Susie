package io.github.lizewskik.susieserver.keycloak.security.dto.response;

import io.github.lizewskik.susieserver.keycloak.security.dto.SimpleRoleRepresentation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.keycloak.representations.AccessTokenResponse;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccessTokenExtendedResponse extends AccessTokenResponse {

    public AccessTokenExtendedResponse(AccessTokenResponse base) {
        super();
        this.token = base.getToken();
        this.refreshToken = base.getRefreshToken();
        this.tokenType = base.getTokenType();
        this.expiresIn = base.getExpiresIn();
        this.refreshExpiresIn = base.getRefreshExpiresIn();
        this.idToken = base.getIdToken();
        this.notBeforePolicy = base.getNotBeforePolicy();
        this.sessionState = base.getSessionState();
        this.scope = base.getScope();
        this.error = base.getError();
        this.errorDescription = base.getErrorDescription();
        this.errorUri = base.getErrorUri();
    }

    private List<SimpleRoleRepresentation> userRoles;
}
