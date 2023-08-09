package io.github.lizewskik.susieserver.resource.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectDTO {

    private Integer projectID;
    private String name;
    private String description;
}
