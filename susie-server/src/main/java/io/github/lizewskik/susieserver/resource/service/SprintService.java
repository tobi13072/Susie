package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.dto.SprintDTO;

import java.util.List;

public interface SprintService {

    SprintDTO getActiveSprint();
    List<SprintDTO> getAllNonActivatedSprints();
    SprintDTO createSprint(SprintDTO sprintDTO);
    void addIssueToSprint(Integer issueID, Integer sprintID);
    void startSprint(Integer sprintID);
    void stopSprint(Integer sprintID);
}
