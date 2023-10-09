package io.github.lizewskik.susieserver.resource.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserExcludingDTO {

    private String userUUID;
    private Integer projectID;
}
