package io.github.lizewskik.susieserver.keycloak.security.controller;

import io.github.lizewskik.susieserver.keycloak.security.dto.request.ExtendPermissionRequest;
import io.github.lizewskik.susieserver.keycloak.security.dto.request.RegistrationRequest;
import io.github.lizewskik.susieserver.keycloak.security.dto.request.SignInRequest;
import io.github.lizewskik.susieserver.keycloak.security.dto.response.AccessTokenExtendedResponse;
import io.github.lizewskik.susieserver.keycloak.security.dto.response.AccountDeletionResponse;
import io.github.lizewskik.susieserver.keycloak.security.dto.response.RegistrationResponse;
import io.github.lizewskik.susieserver.keycloak.security.service.KeycloakService;
import io.github.lizewskik.susieserver.resource.dto.UserDTO;
import io.github.lizewskik.susieserver.resource.service.ProjectService;
import io.github.lizewskik.susieserver.resource.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.SM_PERMISSION;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class KeycloakController {

    private final KeycloakService keycloakService;
    private final UserService userService;
    private final ProjectService projectService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest user) {
        Map.Entry<Integer, RegistrationResponse> response = keycloakService.register(user);
        return ResponseEntity.status(response.getKey()).body(response.getValue());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AccessTokenExtendedResponse> signIn(@RequestBody SignInRequest credentials) {
        return ResponseEntity.ok(keycloakService.signIn(credentials));
    }

    @PostMapping("/refresh")
    public Map<String, String> refreshToken(@RequestParam String refreshToken) throws URISyntaxException, ExecutionException, InterruptedException {
        return keycloakService.refreshToken(refreshToken);
    }

    @GetMapping("/user-info")
    public ResponseEntity<UserDTO> userInfo() {
        return ResponseEntity.ok(userService.getCurrentLoggedUser());
    }

    @PreAuthorize(SM_PERMISSION)
    @PatchMapping("/user/permission")
    public void grantNewPermissionToUser(@RequestBody ExtendPermissionRequest permission) {
        Set<String> projectUsersUUIDs = userService.getAllProjectUsersUUIDs(permission.getProjectID());
        keycloakService.grantNewPermissionToUser(permission.getUserUUID(), permission.getRoleName(), projectUsersUUIDs);
    }

    @PreAuthorize(SM_PERMISSION)
    @PatchMapping("/user/permission-revoke")
    public void revokePermissionFromUser(@RequestBody ExtendPermissionRequest revokedPermission) {
        keycloakService.revokeUserRole(revokedPermission.getUserUUID(), revokedPermission.getRoleName());
    }

    @DeleteMapping("/account-deletion")
    public ResponseEntity<AccountDeletionResponse> deleteAccount() {
        projectService.deleteUserFromAllProjects(userService.getCurrentLoggedUser().getEmail());
        return ResponseEntity.ok(keycloakService.deleteAccount(userService.getCurrentLoggedUser().getUuid()));
    }
}
