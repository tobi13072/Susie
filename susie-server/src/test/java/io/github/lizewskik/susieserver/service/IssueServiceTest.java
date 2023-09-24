package io.github.lizewskik.susieserver.service;

import io.github.lizewskik.susieserver.builder.IssueBuilder;
import io.github.lizewskik.susieserver.builder.UserBuilder;
import io.github.lizewskik.susieserver.config.TestConfiguration;
import io.github.lizewskik.susieserver.resource.domain.Backlog;
import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.domain.dictionary.IssueStatusID;
import io.github.lizewskik.susieserver.resource.dto.IssueDTO;
import io.github.lizewskik.susieserver.resource.dto.IssueGeneralDTO;
import io.github.lizewskik.susieserver.resource.dto.request.IssueMRO;
import io.github.lizewskik.susieserver.resource.repository.BacklogRepository;
import io.github.lizewskik.susieserver.resource.repository.IssuePriorityRepository;
import io.github.lizewskik.susieserver.resource.repository.IssueRepository;
import io.github.lizewskik.susieserver.resource.repository.IssueStatusRepository;
import io.github.lizewskik.susieserver.resource.repository.IssueTypeRepository;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import io.github.lizewskik.susieserver.resource.service.IssueService;
import io.github.lizewskik.susieserver.resource.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static io.github.lizewskik.susieserver.builder.DictionaryBuilder.createAllIssuePriorities;
import static io.github.lizewskik.susieserver.builder.DictionaryBuilder.createAllIssueStatuses;
import static io.github.lizewskik.susieserver.builder.DictionaryBuilder.createAllIssueTypes;
import static io.github.lizewskik.susieserver.builder.IssueBuilder.createDefaultIssue;
import static io.github.lizewskik.susieserver.builder.IssueBuilder.createIssueWithFakeIssuePriorityID;
import static io.github.lizewskik.susieserver.builder.IssueBuilder.createIssueWithFakeIssueTypeID;
import static io.github.lizewskik.susieserver.builder.ProjectBuilder.createProjectEntity;
import static io.github.lizewskik.susieserver.builder.UserBuilder.createCurrentLoggedInUser;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_PRIORITY_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_TYPE_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.PROJECT_DOES_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfiguration.class)
public class IssueServiceTest {

    @Autowired
    private IssueTypeRepository issueTypeRepository;

    @Autowired
    private IssueStatusRepository issueStatusRepository;

    @Autowired
    private IssuePriorityRepository issuePriorityRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueRepository issueRepository;

    @MockBean
    private UserService userService;

    private Project globalProject;

    @BeforeEach
    void setUp() {

        // dictionaries initialization
        issueTypeRepository.saveAll(createAllIssueTypes());
        issueStatusRepository.saveAll(createAllIssueStatuses());
        issuePriorityRepository.saveAll(createAllIssuePriorities());

        // global project configuration
        globalProject = projectRepository.save(createProjectEntity());
        Backlog backlog = globalProject.getBacklog();
        backlog.setProject(globalProject);
        backlogRepository.save(backlog);

        // mocks
        when(userService.getCurrentLoggedUser()).thenReturn(createCurrentLoggedInUser());

        issuePriorityRepository.flush();
        issueStatusRepository.flush();
        issueTypeRepository.flush();
        backlogRepository.flush();
        projectRepository.flush();
        issueRepository.flush();
    }

    @Test
    public void getIssuesGeneral_happyPathTest() {

        //given
        int issuesAmount = 10;
        List<IssueMRO> issueMROList = IssueBuilder.createManyIssues(issuesAmount, globalProject.getId());
        long expectedIssueAmount = issueRepository.count() + issuesAmount;
        Integer projectID = globalProject.getId();

        //when
        issueMROList.forEach(issue -> issueService.createIssue(issue));
        long actualIssueAmount = issueRepository.count();
        List<IssueGeneralDTO> actualReturnList = issueService.getIssuesGeneral(projectID);

        //then
        assertEquals(expectedIssueAmount, actualIssueAmount);
        assertEquals(issuesAmount, actualReturnList.size());
        actualReturnList.forEach(issue -> assertEquals(IssueStatusID.TO_DO.getStatusID(), issue.getIssueStatusID()));
    }

    @Test
    public void getIssuesGeneral_throwsProjectDoesNotExistsExceptionTest() {

        //given
        Integer fakeProjectID = globalProject.getId() + 1;

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> issueService.getIssuesGeneral(fakeProjectID));

        //then
        assertEquals(PROJECT_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void getIssueDetails_happyPathTest() {

        //given
        IssueMRO issueMRO = createDefaultIssue(globalProject.getId());
        Integer issueID = issueService.createIssue(issueMRO).getIssueID();

        //when
        IssueDTO actualReturnedIssueDetails = issueService.getIssueDetails(issueID);

        //then
        assertEquals(issueMRO.getName(), actualReturnedIssueDetails.getName());
        assertEquals(issueMRO.getDescription(), actualReturnedIssueDetails.getDescription());
        assertEquals(issueMRO.getEstimation(), actualReturnedIssueDetails.getEstimation());
        assertEquals(issueMRO.getProjectID(), actualReturnedIssueDetails.getProjectID());
        assertEquals(issueMRO.getIssueTypeID(), actualReturnedIssueDetails.getIssueTypeID());
        assertEquals(issueMRO.getIssuePriorityID(), actualReturnedIssueDetails.getIssuePriorityID());
        assertEquals(IssueStatusID.TO_DO.getStatusID(), actualReturnedIssueDetails.getIssueStatusID());
    }

    @Test
    public void getIssueDetails_throwsIssueDoesNotExistsExceptionTest() {

        //given
        Integer fakeIssueID = -10;

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> issueService.getIssueDetails(fakeIssueID));

        //then
        assertEquals(ISSUE_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void createIssue_happyPathTest() {

        //given
        Integer projectID = globalProject.getId();
        IssueMRO issueMRO = createDefaultIssue(projectID);
        long expectedIssueAmount = issueRepository.count() + 1;

        //when
        IssueDTO createdIssue = issueService.createIssue(issueMRO);
        long actualIssueAmount = issueRepository.count();

        //then
        assertEquals(expectedIssueAmount, actualIssueAmount);
        assertEquals(issueMRO.getName(), createdIssue.getName());
        assertEquals(issueMRO.getDescription(), createdIssue.getDescription());
        assertEquals(issueMRO.getEstimation(), createdIssue.getEstimation());
        assertEquals(issueMRO.getProjectID(), createdIssue.getProjectID());
        assertEquals(issueMRO.getIssueTypeID(), createdIssue.getIssueTypeID());
        assertEquals(issueMRO.getIssuePriorityID(), createdIssue.getIssuePriorityID());
    }

    @Test
    public void createIssue_throwsProjectDoesNotExistsExceptionTest() {

        //given
        Integer fakeProjectID = globalProject.getId() + 1;
        IssueMRO issueMRO = createDefaultIssue(fakeProjectID);

        //when
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> issueService.createIssue(issueMRO));

        //then
        assertEquals(PROJECT_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void createIssue_throwsIssueTypeDoesNotExistsExceptionTest() {

        //given
        IssueMRO issueMRO = createIssueWithFakeIssueTypeID(globalProject.getId());

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> issueService.createIssue(issueMRO));

        //then
        assertEquals(ISSUE_TYPE_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void createIssue_throwsIssuePriorityDoesNotExistsExceptionTest() {

        //given
        IssueMRO issueMRO = createIssueWithFakeIssuePriorityID(globalProject.getId());

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> issueService.createIssue(issueMRO));

        //then
        assertEquals(ISSUE_PRIORITY_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void deleteIssue_happyPathTest() {

        //given
        Integer projectID = globalProject.getId();
        IssueMRO issueMRO = createDefaultIssue(projectID);

        //when
        Integer issueID = issueService.createIssue(issueMRO).getIssueID();
        long expectedIssueAmount = issueRepository.count() - 1;
        issueService.deleteIssue(issueID);
        long actualIssueAmount = issueRepository.count();

        //then
        assertEquals(expectedIssueAmount, actualIssueAmount);
    }

    @Test
    public void assignCurrentUserToIssue_happyPathTest() {

        //given
        Integer issueID = issueService.createIssue(createDefaultIssue(globalProject.getId())).getIssueID();
        String uuid = UUID.randomUUID().toString();
        when(userService.getCurrentLoggedUser()).thenReturn(UserBuilder.createAnotherCurrentLoggedInUser(uuid));

        //when
        issueService.assignCurrentUserToIssue(issueID);
        Issue actualIssueAfterAssignment = issueRepository.findById(issueID).isPresent() ? issueRepository.findById(issueID).get() : null;

        //then
        assertNotNull(actualIssueAfterAssignment);
        assertEquals(uuid, actualIssueAfterAssignment.getAssigneeID());
    }

    @Test
    public void assignCurrentUserToIssue_throwsIssueDoesNotExistsExceptionTest() {

        //given
        Integer fakeIssueID = -10;

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> issueService.assignCurrentUserToIssue(fakeIssueID));

        //then
        assertEquals(ISSUE_DOES_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void deleteUserToIssueAssignment_happyPathTest() {

        //given
        Integer issueID = issueService.createIssue(createDefaultIssue(globalProject.getId())).getIssueID();
        String uuid = UUID.randomUUID().toString();
        when(userService.getCurrentLoggedUser()).thenReturn(UserBuilder.createAnotherCurrentLoggedInUser(uuid));
        issueService.assignCurrentUserToIssue(issueID);

        //when
        issueService.deleteUserToIssueAssignment(issueID);
        Issue actualIssueAfterAssignmentRemoval = issueRepository.findById(issueID).isPresent() ? issueRepository.findById(issueID).get() : null;

        //then
        assertNotNull(actualIssueAfterAssignmentRemoval);
        assertNull(actualIssueAfterAssignmentRemoval.getAssigneeID());
    }

    @Test
    public void deleteUserToIssueAssignment_throwsIssueDoesNotExistsExceptionTest() {

        //given
        Integer fakeIssueID = -10;

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> issueService.deleteUserToIssueAssignment(fakeIssueID));

        //then
        assertEquals(ISSUE_DOES_NOT_EXISTS, exception.getMessage());
    }
}
