package io.github.lizewskik.susieserver.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}
