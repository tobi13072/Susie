package io.github.lizewskik.susieserver.resource.controller;

import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.dto.ProjectDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.github.lizewskik.susieserver.keycloak.security.dictionary.KeycloakDictionary.SM_PERMISSION;

@RestController
@RequestMapping("/api/scrum-project")
@RequiredArgsConstructor
public class ProjectRestController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping
    @PreAuthorize(SM_PERMISSION)
    public ResponseEntity<Project> createProject(@RequestBody ProjectDTO project) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.createProject(project));
    }

    @PutMapping
    @PreAuthorize(SM_PERMISSION)
    public ResponseEntity<Project> updateProject(@RequestBody ProjectDTO project) {
        return ResponseEntity.ok(projectService.updateProject(project));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(SM_PERMISSION)
    public ResponseEntity<Project> deleteProject(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.deleteProject(id));
    }

    @GetMapping("/user-association")
    @PreAuthorize(SM_PERMISSION)
    public void associateUserWithProject(@RequestParam String email, @RequestParam Integer projectID) {
        projectService.associateUserWithProject(email, projectID);
    }
}
