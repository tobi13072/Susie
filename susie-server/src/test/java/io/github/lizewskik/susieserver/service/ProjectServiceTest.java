package io.github.lizewskik.susieserver.service;

import io.github.lizewskik.susieserver.builder.UserBuilder;
import io.github.lizewskik.susieserver.config.TestConfiguration;
import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.dto.ProjectDTO;
import io.github.lizewskik.susieserver.resource.dto.UserDTO;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import io.github.lizewskik.susieserver.resource.service.ProjectService;
import io.github.lizewskik.susieserver.resource.service.UserService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static io.github.lizewskik.susieserver.builder.ProjectBuilder.createAnotherProject;
import static io.github.lizewskik.susieserver.builder.ProjectBuilder.createProject;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ACTION_NOT_ALLOWED;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.PROJECT_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.PROJECT_NAME_NOT_UNIQUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfiguration.class)
@Transactional
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        when(userService.getCurrentLoggedUser()).thenReturn(UserBuilder.createCurrentLoggedInUser());
    }

    @Test
    public void getAllProjectsTest() {

        //given
        ProjectDTO exampleProject = createProject();
        long expectedProjectsAmount = 3;
        List<ProjectDTO> projects = new ArrayList<>(Collections.nCopies(3, exampleProject));

        //when
        projects.forEach(pro -> {
            pro.setName(RandomStringUtils.random(3, true, true));
            projectService.createProject(pro);
        });
        long actualProjectsAmount = projectRepository.count();

        //then
        assertEquals(expectedProjectsAmount, actualProjectsAmount);
    }

    @Test
    public void createProject_happyPathTest() {

        //given
        ProjectDTO expected = createProject();
        long expectedProjectAmount = projectRepository.count() + 1;

        //when
        ProjectDTO actual = projectService.createProject(expected);
        long actualProjectsAmount = projectRepository.count();

        //then
        assertNotNull(actual.getProjectID());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expectedProjectAmount, actualProjectsAmount);
    }

    @Test
    public void createProject_throwsNotUniqueProjectNameExceptionTest() {

        //given
        ProjectDTO duplicatedProjectName = createProject();

        //when
        projectService.createProject(duplicatedProjectName);
        Exception exception = assertThrows(RuntimeException.class, () -> projectService.createProject(duplicatedProjectName));

        //then
        assertEquals(PROJECT_NAME_NOT_UNIQUE, exception.getMessage());
    }

    @Test
    public void deleteProject_happyPathTest() {

        //given
        ProjectDTO project = createProject();

        //when
        ProjectDTO projectToBeDeleted = projectService.createProject(project);
        long expectedProjectsAmount = projectRepository.count() - 1;
        projectService.deleteProject(projectToBeDeleted.getProjectID());
        long actualProjectsAmount = projectRepository.count();

        //then
        assertEquals(expectedProjectsAmount, actualProjectsAmount);
    }

    @Test
    public void deleteProject_throwsProjectDoesNotExistExceptionTest() {

        //given
        ProjectDTO project = createProject();

        //when
        ProjectDTO createdProject = projectService.createProject(project);
        Integer notExistingProjectID = createdProject.getProjectID() + 1;
        Exception exception = assertThrows(RuntimeException.class, () -> projectService.deleteProject(notExistingProjectID));

        //then
        assertEquals(PROJECT_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void updateProject_happyPathTest() {

        //given
        ProjectDTO baseProject = createProject();
        ProjectDTO expectedUpdatedProject = createAnotherProject();

        //when
        Integer projectID = projectService.createProject(baseProject).getProjectID();
        expectedUpdatedProject.setProjectID(projectID);
        ProjectDTO actualUpdatedProject = projectService.updateProject(expectedUpdatedProject);

        //then
        assertEquals(expectedUpdatedProject.getProjectID(), actualUpdatedProject.getProjectID());
        assertEquals(expectedUpdatedProject.getName(), actualUpdatedProject.getName());
        assertEquals(expectedUpdatedProject.getDescription(), actualUpdatedProject.getDescription());
    }

    @Test
    public void updateProject_throwsProjectDoesNotExistsExceptionTest() {

        //given
        ProjectDTO baseProject = createProject();
        ProjectDTO updatedProject = createAnotherProject();

        //when
        Integer fakeProjectID = projectService.createProject(baseProject).getProjectID() + 1;
        updatedProject.setProjectID(fakeProjectID);
        Exception exception = assertThrows(RuntimeException.class, () -> projectService.updateProject(updatedProject));

        //then
        assertEquals(PROJECT_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void associateUserWithProject_happyPathTest() {

        //given
        ProjectDTO project = createProject();
        String userEmail = "gallus.anonimus@test.com";
        String userUUID = UUID.randomUUID().toString();
        UserDTO userReturnedObject = new UserDTO();
        userReturnedObject.setEmail(userEmail);
        userReturnedObject.setUuid(userUUID);
        when(userService.isProjectOwner(any())).thenReturn(true);
        when(userService.getUserByEmail(userEmail)).thenReturn(userReturnedObject);

        //when
        Integer projectID = projectService.createProject(project).getProjectID();
        projectService.associateUserWithProject(userEmail, projectID);
        Project actualReturnedProject = projectRepository.getReferenceById(projectID);

        //then
        assertTrue(actualReturnedProject.getUserIDs().contains(userUUID));
    }

    @Test
    public void associateUserWithProject_throwsActionNotAllowedExceptionTest() {

        //given
        when(userService.isProjectOwner(any())).thenReturn(false);

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> projectService.associateUserWithProject(null, null));

        //then
        assertEquals(ACTION_NOT_ALLOWED, exception.getMessage());
    }

    @Test
    public void associateUserWithProject_throwsProjectDoesNotExistsExceptionTest() {

        //given
        when(userService.isProjectOwner(any())).thenReturn(true);
        Integer fakeProjectID = 1;

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> projectService.associateUserWithProject(null, fakeProjectID));

        //then
        assertEquals(PROJECT_DOES_NOT_EXISTS, exception.getMessage());
    }
}
