package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.dto.ProjectDTO;
import io.github.lizewskik.susieserver.resource.dto.ProjectDetailsDTO;
import io.github.lizewskik.susieserver.resource.dto.request.ProjectCreationRequest;

import java.util.List;

public interface ProjectService {

    ProjectDetailsDTO getProjectDetails(Integer projectID);
    ProjectDTO createProject(ProjectCreationRequest projectDTO);
    ProjectDTO updateProject(ProjectDTO projectDTO);
    ProjectDTO deleteProject(Integer projectID);
    List<ProjectDTO> getAllProjects();
    void associateUserWithProject(String email, Integer projectID);
}
