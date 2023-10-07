package io.github.lizewskik.susieserver.service;

import io.github.lizewskik.susieserver.builder.SprintBuilder;
import io.github.lizewskik.susieserver.config.TestConfiguration;
import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.domain.Sprint;
import io.github.lizewskik.susieserver.resource.dto.SprintDTO;
import io.github.lizewskik.susieserver.resource.repository.IssueRepository;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import io.github.lizewskik.susieserver.resource.repository.SprintRepository;
import io.github.lizewskik.susieserver.resource.service.SprintService;
import io.github.lizewskik.susieserver.resource.service.UserService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static io.github.lizewskik.susieserver.builder.IssueBuilder.createIssueEntity;
import static io.github.lizewskik.susieserver.builder.IssueBuilder.createIssueEntityWithSprint;
import static io.github.lizewskik.susieserver.builder.ProjectBuilder.createProjectEntity;
import static io.github.lizewskik.susieserver.builder.SprintBuilder.SPRINT_NAME;
import static io.github.lizewskik.susieserver.builder.SprintBuilder.createSprint;
import static io.github.lizewskik.susieserver.builder.SprintBuilder.createSprintEntity;
import static io.github.lizewskik.susieserver.builder.UserBuilder.createCurrentLoggedInUser;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ACTIVE_SPRINT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.EMPTY_SPRINT;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_ALREADY_HAS_SPRINT;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.PROJECT_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_NAME_NOT_UNIQUE;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_NOT_ACTIVE;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_START_DATE_IN_THE_FUTURE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = TestConfiguration.class)
@Transactional
public class SprintServiceTest {

    @Autowired
    private SprintService sprintService;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private IssueRepository issueRepository;

    @MockBean
    private UserService userService;

    private Project defaultScrumProject;

    @BeforeEach
    void setUp() {

        Mockito.when(userService.getCurrentLoggedUser()).thenReturn(createCurrentLoggedInUser());

        Project project = createProjectEntity();
        projectRepository.save(project);
        defaultScrumProject = project;
    }

    @Test
    public void getActiveSprint_happyPathTest() {

        //given
        sprintRepository.save(createSprintEntity(defaultScrumProject, Boolean.TRUE));

        //when
        SprintDTO activeSprint = sprintService.getActiveSprint(defaultScrumProject.getId());

        //then
        assertEquals(Boolean.TRUE, activeSprint.getActive());
        assertEquals(SPRINT_NAME, activeSprint.getName());
        assertEquals(defaultScrumProject.getId(), activeSprint.getProjectID());
    }

    @Test
    public void getActiveSprint_returnsNullWhenThereIsNoActiveSprint() {

        //given + when
        SprintDTO activeSprint = sprintService.getActiveSprint(defaultScrumProject.getId());

        //then
        Assertions.assertNull(activeSprint);
    }

    @Test
    public void getActiveSprint_throwsProjectDoesNotExistsExceptionText() {

        //given
        Integer fakeProjectID = defaultScrumProject.getId() + 1;

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> sprintService.getActiveSprint(fakeProjectID));

        //then
        assertEquals(PROJECT_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void getAllNonActivatedSprints_happyPathTest() {

        //given
        int sprintsAmount = 3;
        List<SprintDTO> sprintDTOList = new ArrayList<>(Collections.nCopies(3, SprintBuilder.createSprint(defaultScrumProject.getId())));
        long expectedSprintsAmount = sprintRepository.count() + sprintsAmount;

        //when
        List<SprintDTO> createdSprints = new ArrayList<>();
        sprintDTOList.forEach(sprint -> {
            sprint.setName(RandomStringUtils.random(15));
            createdSprints.add(sprintService.createSprint(sprint));
        });
        long actualSprintsAmount = sprintRepository.count();

        //then
        assertEquals(expectedSprintsAmount, actualSprintsAmount);
        createdSprints.forEach(created -> assertEquals(Boolean.FALSE, created.getActive()));
    }

    @Test
    public void createSprint_happyPathTest() {

        //given
        SprintDTO sprintDTO = createSprint(defaultScrumProject.getId());
        long expectedSprintsAmount = sprintRepository.count() + 1;

        //when
        SprintDTO createdSprint = sprintService.createSprint(sprintDTO);
        long actualSprintsAmount = sprintRepository.count();

        //then
        assertEquals(expectedSprintsAmount, actualSprintsAmount);
        assertEquals(sprintDTO.getName(), createdSprint.getName());
        assertEquals(sprintDTO.getStartTime(), createdSprint.getStartTime());
        assertEquals(sprintDTO.getProjectID(), createdSprint.getProjectID());
        assertEquals(Boolean.FALSE, createdSprint.getActive());
    }

    @Test
    public void createSprint_throwsSprintNameIsNotUniqueExceptionTest() {

        //given
        sprintRepository.save(createSprintEntity(defaultScrumProject, Boolean.FALSE));

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> sprintService.createSprint(createSprint(defaultScrumProject.getId())));

        //then
        assertEquals(SPRINT_NAME_NOT_UNIQUE, exception.getMessage());
    }

    @Test
    public void createSprint_throwsProjectDoesNotExistsExceptionTest() {

        //given
        Integer fakeProjectID = defaultScrumProject.getId() + 1;
        SprintDTO sprintDTO = createSprint(fakeProjectID);

        //when
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> sprintService.createSprint(sprintDTO));

        //then
        assertEquals(PROJECT_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void addIssueToSprint_happyPathTest() {

        //given
        Integer sprintID = sprintRepository.save(createSprintEntity(defaultScrumProject, Boolean.FALSE)).getId();
        Integer issueID = issueRepository.save(createIssueEntity()).getId();

        //when
        sprintService.addIssueToSprint(issueID, sprintID);
        Issue issueAfterSave = issueRepository.findById(issueID).isPresent() ? issueRepository.findById(issueID).get() : null;

        //then
        assertNotNull(issueAfterSave);
        assertEquals(sprintID, issueAfterSave.getSprint().getId());
    }

    @Test
    public void addIssueToSprint_throwsIssueDoesNotExistsExceptionTest() {

        //given
        Integer fakeIssueID = -10;

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> sprintService.addIssueToSprint(fakeIssueID, null));

        //then
        assertEquals(ISSUE_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void addIssueToSprint_throwsSprintDoesNotExistsExceptionTest() {

        //given
        Integer issueID = issueRepository.save(createIssueEntity()).getId();
        Integer fakeSprintID = -10;

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> sprintService.addIssueToSprint(issueID, fakeSprintID));

        //then
        assertEquals(SPRINT_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void addIssueToSprint_throwsIssueAlreadyHasSprintExceptionTest() {

        //given
        Sprint sprint = sprintRepository.save(createSprintEntity(defaultScrumProject, Boolean.FALSE));
        Issue issue = issueRepository.save(createIssueEntityWithSprint(sprint));

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> sprintService.addIssueToSprint(issue.getId(), sprint.getId()));

        //then
        assertEquals(ISSUE_ALREADY_HAS_SPRINT, exception.getMessage());
    }

    @Test
    public void startSprint_happyPathTest() {

        //given
        Sprint sprint = sprintRepository.save(createSprintEntity(defaultScrumProject, Boolean.FALSE));
        Issue issue = issueRepository.save(createIssueEntityWithSprint(sprint));
        sprint.setSprintIssues(new HashSet<>(List.of(issue)));
        sprintRepository.save(sprint);

        //when
        sprintService.startSprint(sprint.getId());
        boolean existsActiveSprint = sprintRepository.findByActiveAndProject(Boolean.TRUE, defaultScrumProject).isPresent() ? sprintRepository.findByActiveAndProject(Boolean.TRUE, defaultScrumProject).get().getActive() : false;

        //then
        assertTrue(existsActiveSprint);
    }

    @Test
    public void startSprint_throwsActiveSprintAlreadyExistsExceptionTest() {

        //given
        sprintRepository.save(createSprintEntity(defaultScrumProject, Boolean.TRUE));
        Integer unactivatedSprintID = sprintRepository.save(createSprintEntity(defaultScrumProject, Boolean.FALSE)).getId();

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> sprintService.startSprint(unactivatedSprintID));

        //then
        assertEquals(ACTIVE_SPRINT_EXISTS, exception.getMessage());
    }

    @Test
    public void startSprint_throwsSprintDoesNotExistsExceptionTest() {

        //given
        Integer fakeSprintID = -10;

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> sprintService.startSprint(fakeSprintID));

        //then
        assertEquals(SPRINT_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void startSprint_throwsEmptySprintExceptionTest() {

        //given
        Integer sprintWithoutIssuesID = sprintRepository.save(createSprintEntity(defaultScrumProject, Boolean.FALSE)).getId();

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> sprintService.startSprint(sprintWithoutIssuesID));

        //then
        assertEquals(EMPTY_SPRINT, exception.getMessage());
    }

    @Test
    public void startSprint_throwsSprintStartDateInTheFutureExceptionTest() {

        //given
        ZonedDateTime futureDate = ZonedDateTime.now().plusDays(1);

        Sprint sprint = sprintRepository.save(createSprintEntity(defaultScrumProject, Boolean.FALSE));
        Issue issue = issueRepository.save(createIssueEntityWithSprint(sprint));
        sprint.setSprintIssues(new HashSet<>(List.of(issue)));
        sprint.setStartDate(futureDate);
        sprintRepository.save(sprint);

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> sprintService.startSprint(sprint.getId()));

        //then
        assertEquals(SPRINT_START_DATE_IN_THE_FUTURE, exception.getMessage());
    }

    @Test
    public void stopSprint_happyPathTest() {

        //given
        Integer activeSprintID = sprintRepository.save(createSprintEntity(defaultScrumProject, Boolean.TRUE)).getId();

        //when
        sprintService.stopSprint(activeSprintID);
        Sprint afterStop = sprintRepository.findById(activeSprintID).isPresent() ? sprintRepository.findById(activeSprintID).get() : null;

        //then
        assertNotNull(afterStop);
        assertFalse(afterStop.getActive());
    }

    @Test
    public void stopSprint_throwsSprintDoesNotExistsExceptionTest() {

        //given
        Integer fakeSprintID = -10;

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> sprintService.stopSprint(fakeSprintID));

        //then
        assertEquals(SPRINT_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void stopSprint_throwsSprintIsNotActiveExceptionTest() {

        //given
        Integer deactivatedSprintID = sprintRepository.save(createSprintEntity(defaultScrumProject, Boolean.FALSE)).getId();

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> sprintService.stopSprint(deactivatedSprintID));

        //then
        assertEquals(SPRINT_NOT_ACTIVE, exception.getMessage());
    }
}
