package io.github.lizewskik.susieserver.resource.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class SprintCreationRequest {

    private String name;
    private Integer projectID;
    private ZonedDateTime startTime;
}
