package io.github.lizewskik.susieserver.resource.controller;

import io.github.lizewskik.susieserver.resource.dto.ProjectDTO;
import io.github.lizewskik.susieserver.resource.dto.ProjectDetailsDTO;
import io.github.lizewskik.susieserver.resource.dto.UserAssociationDTO;
import io.github.lizewskik.susieserver.resource.dto.UserExcludingDTO;
import io.github.lizewskik.susieserver.resource.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.SM_PERMISSION;

@RestController
@RequestMapping("/api/scrum-project")
@RequiredArgsConstructor
public class ProjectRestController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping
    @PreAuthorize(SM_PERMISSION)
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO project) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.createProject(project));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailsDTO> getProjectDetailsByProjectID(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.getProjectDetails(id));
    }

    @PutMapping
    @PreAuthorize(SM_PERMISSION)
    public ResponseEntity<ProjectDTO> updateProject(@RequestBody ProjectDTO project) {
        return ResponseEntity.ok(projectService.updateProject(project));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(SM_PERMISSION)
    public ResponseEntity<ProjectDTO> deleteProject(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.deleteProject(id));
    }

    @PostMapping("/user-association")
    @PreAuthorize(SM_PERMISSION)
    public void associateUserWithProject(@RequestBody UserAssociationDTO associationRequest) {
        projectService.associateUserWithProject(associationRequest.getEmail(), associationRequest.getProjectID());
    }

    @DeleteMapping("/delete-user")
    @PreAuthorize(SM_PERMISSION)
    public void deleteUserFromProject(@RequestBody UserExcludingDTO exclusionRequest) {
        projectService.deleteUserFromProject(exclusionRequest.getUserUUID(), exclusionRequest.getProjectID());
    }
}
