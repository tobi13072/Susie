package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.dto.ProjectDTO;
import io.github.lizewskik.susieserver.resource.dto.request.ProjectCreationRequest;

import java.util.List;

public interface ProjectService {

    Project createProject(ProjectCreationRequest projectDTO);
    Project updateProject(ProjectDTO projectDTO);
    Project deleteProject(Integer projectID);
    List<ProjectDTO> getAllProjects();
    void associateUserWithProject(String email, Integer projectID);
}
