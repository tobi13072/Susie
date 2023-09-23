package io.github.lizewskik.susieserver.builder;

import io.github.lizewskik.susieserver.resource.dto.ProjectDTO;

public class ProjectBuilder {

    public static final String PROJECT_NAME = "TestName";
    public static final String PROJECT_DESCRIPTION = "Test description";
    public static final String ANOTHER_PROJECT_NAME = "Another project name";
    public static final String ANOTHER_PROJECT_DESCRIPTION = "Another test project description";

    public static ProjectDTO createProject() {
        return ProjectDTO.builder()
                .name(PROJECT_NAME)
                .description(PROJECT_DESCRIPTION)
                .build();
    }

    public static ProjectDTO createAnotherProject() {
        return ProjectDTO.builder()
                .name(ANOTHER_PROJECT_NAME)
                .description(ANOTHER_PROJECT_DESCRIPTION)
                .build();
    }
}
