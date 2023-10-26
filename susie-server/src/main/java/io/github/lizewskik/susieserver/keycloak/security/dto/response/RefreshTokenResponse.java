package io.github.lizewskik.susieserver.keycloak.security.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponse {

    @JsonProperty("access_token")
    protected String token;

    @JsonProperty("expires_in")
    protected long expiresIn;

    @JsonProperty("refresh_token")
    protected String refreshToken;

    @JsonProperty("refresh_expires_in")
    protected long refreshExpiresIn;
}
