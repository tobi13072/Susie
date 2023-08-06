package io.github.lizewskik.susieserver.keycloak.security.dto.request;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}
