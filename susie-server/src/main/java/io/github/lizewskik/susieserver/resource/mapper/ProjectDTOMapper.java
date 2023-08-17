package io.github.lizewskik.susieserver.resource.mapper;

import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.dto.ProjectDTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectDTOMapper {

    public ProjectDTO map(Project from) {
        return ProjectDTO.builder()
                .projectID(from.getId())
                .name(from.getName())
                .description(from.getDescription())
                .build();
    }
}
