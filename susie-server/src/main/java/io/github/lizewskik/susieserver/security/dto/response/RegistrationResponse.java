package io.github.lizewskik.susieserver.security.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponse {

    private String result;
    private Boolean success;
}

