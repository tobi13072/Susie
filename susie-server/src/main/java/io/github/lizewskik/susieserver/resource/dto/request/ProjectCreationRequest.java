package io.github.lizewskik.susieserver.resource.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectCreationRequest {

    private String name;
    private String description;
}
