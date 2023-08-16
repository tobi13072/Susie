package io.github.lizewskik.susieserver.keycloak.security.dto.request;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
