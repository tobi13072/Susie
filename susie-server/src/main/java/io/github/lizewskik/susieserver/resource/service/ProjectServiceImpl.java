package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.Backlog;
import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.dto.ProjectDTO;
import io.github.lizewskik.susieserver.resource.dto.ProjectDetailsDTO;
import io.github.lizewskik.susieserver.resource.dto.UserDTO;
import io.github.lizewskik.susieserver.resource.mapper.ProjectDTOMapper;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ACTION_NOT_ALLOWED;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.PROJECT_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.PROJECT_NAME_NOT_UNIQUE;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectDTOMapper projectDTOMapper;
    private final UserService userService;

    @Override
    public ProjectDetailsDTO getProjectDetails(Integer projectID) {
        Project project = projectRepository.findById(projectID)
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));
        ProjectDetailsDTO detailsDTO = new ProjectDetailsDTO();
        detailsDTO.setProjectID(project.getId());
        detailsDTO.setName(project.getName());
        detailsDTO.setDescription(project.getDescription());
        detailsDTO.setOwner(userService.getUserByUUID(project.getProjectOwner()));
        detailsDTO.setMembers(mapUserUUIDsToUserDTOs(project.getUserIDs()));
        return detailsDTO;
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectCreationRequest) {

        String currentLoggedUser = userService.getCurrentLoggedUser().getUuid();
        if (projectRepository.existsByNameAndProjectOwner(projectCreationRequest.getName(), currentLoggedUser)) {
            throw new RuntimeException(PROJECT_NAME_NOT_UNIQUE);
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

        return ProjectDTO.builder()
                .projectID(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .build();
    }

    @Override
    public ProjectDTO updateProject(ProjectDTO projectDTO) {

        Project updated = projectRepository.findById(projectDTO.getProjectID())
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));
        updated.setName(projectDTO.getName());
        updated.setDescription(projectDTO.getDescription());
        projectRepository.save(updated);
        return ProjectDTO.builder()
                .projectID(updated.getId())
                .name(updated.getName())
                .description(updated.getDescription())
                .build();
    }

    @Override
    public ProjectDTO deleteProject(Integer projectID) {

        Project project = projectRepository.findById(projectID)
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));
        projectRepository.deleteById(projectID);
        return ProjectDTO.builder()
                .projectID(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .build();
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
            throw new RuntimeException(ACTION_NOT_ALLOWED);
        }

        Project updated = projectRepository.findById(projectID)
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));
        String userUUID = userService.getUserByEmail(email).getUuid();
        Set<String> usersAssociatedWithProject = updated.getUserIDs();
        usersAssociatedWithProject.add(userUUID);
        updated.setUserIDs(usersAssociatedWithProject);
        projectRepository.save(updated);
    }

    private List<UserDTO> mapUserUUIDsToUserDTOs(Set<String> uuids) {
        return uuids.stream()
                .map(userService::getUserByUUID)
                .collect(Collectors.toList());
    }
}
