package io.github.lizewskik.susieserver.builder;

import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.domain.Sprint;
import io.github.lizewskik.susieserver.resource.dto.SprintDTO;

import java.time.ZonedDateTime;
import java.util.HashSet;

public class SprintBuilder {

    public static final String SPRINT_NAME = "Test sprint name";
    public static final ZonedDateTime SPRINT_START_TIME = ZonedDateTime.now();

    public static SprintDTO createSprint(Integer projectID) {
        return SprintDTO.builder()
                .name(SPRINT_NAME)
                .startTime(SPRINT_START_TIME)
                .projectID(projectID)
                .build();
    }

    public static Sprint createSprintEntity(Project project, Boolean active) {
        return Sprint.builder()
                .name(SPRINT_NAME)
                .startDate(SPRINT_START_TIME)
                .project(project)
                .sprintIssues(new HashSet<>())
                .active(active)
                .build();
    }
}
