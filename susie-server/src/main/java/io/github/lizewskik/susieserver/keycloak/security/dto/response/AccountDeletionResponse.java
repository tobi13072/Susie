package io.github.lizewskik.susieserver.keycloak.security.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDeletionResponse {

    private String result;
    private Integer internalStatus;
    private String reasonPhrase;
    private Boolean success;
}
