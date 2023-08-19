package io.github.lizewskik.susieserver.resource.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IssueCreationRequest {

    private String name;
    private String description;
    private Integer estimation;
    private Integer projectID;
}
