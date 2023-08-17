package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.exception.ProjectAlreadyExistsException;
import io.github.lizewskik.susieserver.resource.domain.Backlog;
import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.dto.ProjectDTO;
import io.github.lizewskik.susieserver.resource.dto.request.ProjectCreationRequest;
import io.github.lizewskik.susieserver.resource.mapper.ProjectDTOMapper;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectDTOMapper projectDTOMapper;
    private final UserService userService;

    @Override
    public Project createProject(ProjectCreationRequest projectCreationRequest) {

        String currentLoggedUser = userService.getCurrentLoggedUser().getUuid();
        if (projectRepository.existsByNameAndProjectOwner(projectCreationRequest.getName(), currentLoggedUser)) {
            throw new ProjectAlreadyExistsException();
        }

        HashSet<String> usersAssociatedWithProject = new HashSet<>();
        usersAssociatedWithProject.add(currentLoggedUser);

        Backlog backlog = new Backlog();
        Project project = Project.builder()
                .name(projectCreationRequest.getName())
                .description(projectCreationRequest.getDescription())
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
    public List<ProjectDTO> getAllProjects() {
        String currentLoggedUser = userService.getCurrentLoggedUser().getUuid();
        return projectRepository.findAllByUserIDsContains(currentLoggedUser)
                .stream()
                .map(projectDTOMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void associateUserWithProject(String email, Integer projectID) {

        if (!userService.isProjectOwner(projectID)) {
            throw new RuntimeException("Current user is not allowed to perform this action");
        }

        String userUUID = userService.getUserByEmail(email).getUuid();
        Project updated = projectRepository.findById(projectID).orElseThrow(RuntimeException::new);

        Set<String> usersAssociatedWithProject = updated.getUserIDs();
        usersAssociatedWithProject.add(userUUID);
        updated.setUserIDs(usersAssociatedWithProject);
        projectRepository.save(updated);
    }
}
