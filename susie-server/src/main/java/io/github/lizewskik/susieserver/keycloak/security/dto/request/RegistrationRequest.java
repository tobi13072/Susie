package io.github.lizewskik.susieserver.keycloak.security.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest implements Serializable {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isScrumMaster;
}
