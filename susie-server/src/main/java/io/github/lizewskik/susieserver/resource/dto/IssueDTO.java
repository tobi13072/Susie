package io.github.lizewskik.susieserver.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {

    private Integer issueID;
    private String name;
    private String description;
    private Integer estimation;
    private UserDTO reporter;
    private UserDTO assignee;
    private Integer projectID;
}
