package io.github.lizewskik.susieserver.keycloak.security.controller;

import io.github.lizewskik.susieserver.keycloak.security.dto.response.RegistrationResponse;
import io.github.lizewskik.susieserver.keycloak.security.dto.UserDTO;
import io.github.lizewskik.susieserver.keycloak.security.dto.request.SignInRequest;
import io.github.lizewskik.susieserver.keycloak.security.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class KeycloakController {

    private final KeycloakService service;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody UserDTO user) {
        Map.Entry<Integer, RegistrationResponse> response = service.register(user);
        return ResponseEntity.status(response.getKey()).body(response.getValue());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AccessTokenResponse> signIn(@RequestBody SignInRequest credentials) {
        return ResponseEntity.ok(service.signIn(credentials));
    }
}
