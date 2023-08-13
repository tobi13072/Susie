package io.github.lizewskik.susieserver.resource.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IssueDTO {

    private String name;
    private String description;
    private Integer estimation;
}
