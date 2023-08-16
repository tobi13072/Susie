package io.github.lizewskik.susieserver.resource.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAssociationDTO {

    private String email;
    private Integer projectID;
}
