package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.dto.SprintDTO;
import io.github.lizewskik.susieserver.resource.dto.request.SprintCreationRequest;

public interface SprintService {

    SprintDTO createSprint(SprintCreationRequest sprintDTO);
    void addIssueToSprint(Integer issueID, Integer sprintID);
}
