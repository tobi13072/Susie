package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.exception.ProjectAlreadyExistsException;
import io.github.lizewskik.susieserver.resource.domain.Backlog;
import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.dto.ProjectDTO;
import io.github.lizewskik.susieserver.resource.repository.BacklogRepository;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    @Override
    public Project createProject(ProjectDTO projectDTO) {

        if (projectRepository.existsByName(projectDTO.getName())) {
            throw new ProjectAlreadyExistsException();
        }

        String currentLoggedUser = userService.getCurrentLoggedUserUUID();
        HashSet<String> usersAssociatedWithProject = new HashSet<>();
        usersAssociatedWithProject.add(currentLoggedUser);

        Backlog backlog = new Backlog();
        Project project = Project.builder()
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .projectOwner(currentLoggedUser)
                .backlog(backlog)
                .userIDs(usersAssociatedWithProject)
                .build();
        projectRepository.save(project);

        return project;
    }

    @Override
    public Project updateProject(ProjectDTO projectDTO) {

        Project updated = projectRepository.findById(projectDTO.getProjectID()).orElseThrow(RuntimeException::new);
        updated.setName(projectDTO.getName());
        updated.setDescription(projectDTO.getDescription());

        projectRepository.save(updated);
        return updated;
    }

    @Override
    public Project deleteProject(Integer projectID) {

        Project project = projectRepository.findById(projectID).orElseThrow(RuntimeException::new);
        projectRepository.deleteById(projectID);
        return project;
    }

    @Override
    public List<Project> getAllProjects() {
        String currentLoggedUser = userService.getCurrentLoggedUserUUID();
        return projectRepository.findAllByUserIDsContains(currentLoggedUser);
    }

    @Override
    public void associateUserWithProject(String email, Integer projectID) {

        String userUUID = userService.getUserUUIDByEmail(email);
        Project updated = projectRepository.findById(projectID).orElseThrow(RuntimeException::new);

        Set<String> usersAssociatedWithProject = updated.getUserIDs();
        usersAssociatedWithProject.add(userUUID);
        updated.setUserIDs(usersAssociatedWithProject);
        projectRepository.save(updated);
    }
}
