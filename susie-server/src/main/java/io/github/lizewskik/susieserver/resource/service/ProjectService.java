package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    Project createProject(ProjectDTO projectDTO);
    Project updateProject(ProjectDTO projectDTO);
    Project deleteProject(Integer projectID);
    List<Project> getAllProjects();
    void associateUserWithProject(String email, Integer projectID);
}
