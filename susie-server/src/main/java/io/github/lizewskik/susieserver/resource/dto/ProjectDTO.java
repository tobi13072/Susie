package io.github.lizewskik.susieserver.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private Integer projectID;
    private String name;
    private String description;
    private String projectGoal;
}
