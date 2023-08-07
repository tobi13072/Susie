package io.github.lizewskik.susieserver.keycloak.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleRoleRepresentation {

    String id;
    String name;
}
