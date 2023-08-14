package io.github.lizewskik.susieserver.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueGeneralDTO {

    private String name;
    private UserDTO assignee;
}
