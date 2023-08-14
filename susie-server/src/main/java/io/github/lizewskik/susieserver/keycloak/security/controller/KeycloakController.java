package io.github.lizewskik.susieserver.keycloak.security.controller;

import io.github.lizewskik.susieserver.keycloak.security.dto.request.RegistrationRequest;
import io.github.lizewskik.susieserver.keycloak.security.dto.request.SignInRequest;
import io.github.lizewskik.susieserver.keycloak.security.dto.response.AccessTokenExtendedResponse;
import io.github.lizewskik.susieserver.keycloak.security.dto.response.RegistrationResponse;
import io.github.lizewskik.susieserver.keycloak.security.service.KeycloakService;
import io.github.lizewskik.susieserver.resource.dto.UserDTO;
import io.github.lizewskik.susieserver.resource.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class KeycloakController {

    private final KeycloakService keycloakService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest user) {
        Map.Entry<Integer, RegistrationResponse> response = keycloakService.register(user);
        return ResponseEntity.status(response.getKey()).body(response.getValue());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AccessTokenExtendedResponse> signIn(@RequestBody SignInRequest credentials) {
        return ResponseEntity.ok(keycloakService.signIn(credentials));
    }

    @GetMapping("/user-info")
    public ResponseEntity<UserDTO> userInfo() {
        return ResponseEntity.ok(userService.getCurrentLoggedUser());
    }

    @GetMapping("/user-info/{uuid}")
    public ResponseEntity<UserDTO> userInfo2(@PathVariable String uuid) {
        return ResponseEntity.ok(userService.getUserByUUID(uuid));
    }
}
