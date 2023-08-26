package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.exception.NullIdentifierException;
import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.domain.IssuePriority;
import io.github.lizewskik.susieserver.resource.domain.IssueStatus;
import io.github.lizewskik.susieserver.resource.domain.IssueType;
import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.domain.Sprint;
import io.github.lizewskik.susieserver.resource.domain.dictionary.IssueStatusID;
import io.github.lizewskik.susieserver.resource.dto.IssueDTO;
import io.github.lizewskik.susieserver.resource.dto.IssueGeneralDTO;
import io.github.lizewskik.susieserver.resource.dto.request.IssueCreationRequest;
import io.github.lizewskik.susieserver.resource.dto.request.IssueUpdateRequest;
import io.github.lizewskik.susieserver.resource.mapper.IssueDTOMapper;
import io.github.lizewskik.susieserver.resource.mapper.IssueGeneralDTOMapper;
import io.github.lizewskik.susieserver.resource.repository.IssuePriorityRepository;
import io.github.lizewskik.susieserver.resource.repository.IssueRepository;
import io.github.lizewskik.susieserver.resource.repository.IssueStatusRepository;
import io.github.lizewskik.susieserver.resource.repository.IssueTypeRepository;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import io.github.lizewskik.susieserver.resource.repository.SprintRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_PRIORITY_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_TYPE_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.NULL_IDENTIFIER;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.PROJECT_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.STATUS_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.STATUS_FLOW_ORDER_VIOLATION;
import static io.github.lizewskik.susieserver.resource.domain.dictionary.IssueStatusID.CODE_REVIEW;
import static io.github.lizewskik.susieserver.resource.domain.dictionary.IssueStatusID.DONE;
import static io.github.lizewskik.susieserver.resource.domain.dictionary.IssueStatusID.IN_PROGRESS;
import static io.github.lizewskik.susieserver.resource.domain.dictionary.IssueStatusID.IN_TESTS;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService{

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final IssueStatusRepository issueStatusRepository;
    private final IssuePriorityRepository issuePriorityRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final SprintRepository sprintRepository;
    private final UserService userService;
    private final IssueDTOMapper issueDTOMapper;
    private final IssueGeneralDTOMapper issueGeneralDTOMapper;

    @Override
    public IssueDTO createIssue(IssueCreationRequest issueDTO) {

        Project project = projectRepository.findById(issueDTO.getProjectID())
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));
        IssueStatus status = issueStatusRepository.findById(IssueStatusID.TO_DO.getStatusID())
                .orElseThrow(() -> new RuntimeException(STATUS_DOES_NOT_EXISTS));

        Optional<Integer> issueType = ofNullable(issueDTO.getIssueTypeID());
        Optional<Integer> issuePriority = ofNullable(issueDTO.getIssuePriorityID());
        IssueType type = null;
        IssuePriority priority = null;

        if (issueType.isPresent()) {
            type = issueTypeRepository.findById(issueDTO.getIssueTypeID())
                    .orElseThrow(() -> new RuntimeException(ISSUE_TYPE_DOES_NOT_EXISTS));
        }

        if (issuePriority.isPresent()) {
            priority = issuePriorityRepository.findById(issueDTO.getIssuePriorityID())
                    .orElseThrow(() -> new RuntimeException(ISSUE_PRIORITY_DOES_NOT_EXISTS));
        }

        Issue issue = Issue.builder()
                .name(issueDTO.getName())
                .description(issueDTO.getDescription())
                .estimation(issueDTO.getEstimation())
                .reporterID(userService.getCurrentLoggedUser().getUuid())
                .issueStatus(isNull(status) ? null : status)
                .issueType(isNull(type) ? null : type)
                .issuePriority(isNull(priority) ? null : priority)
                .backlog(project.getBacklog())
                .build();
        issueRepository.save(issue);
        return issueDTOMapper.map(issue);
    }

    @Override
    public IssueDTO updateIssue(IssueUpdateRequest issueDTO) {

        Issue updated = issueRepository.findById(
                ofNullable(issueDTO.getId()).orElseThrow(
                        () -> new IllegalArgumentException(NULL_IDENTIFIER))
                )
                .orElseThrow(() -> new RuntimeException(ISSUE_DOES_NOT_EXISTS));

        Optional<Integer> issuePriority = ofNullable(issueDTO.getIssuePriorityID());
        IssuePriority priority = null;

        if (issuePriority.isPresent()) {
            priority = issuePriorityRepository.findById(issueDTO.getIssuePriorityID())
                    .orElseThrow(() -> new RuntimeException(ISSUE_PRIORITY_DOES_NOT_EXISTS));
        }

        updated.setName(ofNullable(issueDTO.getName()).orElseThrow());
        updated.setDescription(ofNullable(issueDTO.getDescription()).orElseThrow());
        updated.setEstimation(issueDTO.getEstimation());
        updated.setIssuePriority(isNull(priority) ? null : priority);
        issueRepository.save(updated);
        return issueDTOMapper.map(updated);
    }

    @Override
    public void deleteIssue(Integer issueID) {
        issueRepository.deleteById(issueID);
    }

    @Override
    public IssueDTO getIssueDetails(Integer issueID) {

        Issue issue = issueRepository.findById(issueID)
                .orElseThrow(() -> new RuntimeException(ISSUE_DOES_NOT_EXISTS));
        return issueDTOMapper.map(issue);
    }

    @Override
    public List<IssueGeneralDTO> getIssuesGeneral(Integer projectID) {

        Project project = projectRepository.findById(projectID)
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));
        return project.getBacklog()
                .getIssues()
                .stream()
                .map(issueGeneralDTOMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueGeneralDTO> getGeneralIssuesInfoBySprintID(Integer sprintID) {

        Sprint sprint = sprintRepository.findById(ofNullable(sprintID).orElseThrow(NullIdentifierException::new))
                .orElseThrow(() -> new IllegalArgumentException(SPRINT_DOES_NOT_EXISTS));

        return issueRepository.findAllBySprint(sprint)
                .stream()
                .map(issueGeneralDTOMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void assignCurrentUserToIssue(Integer issueID) {

        Issue updated = issueRepository.findById(issueID)
                .orElseThrow(() -> new RuntimeException(ISSUE_DOES_NOT_EXISTS));
        updated.setAssigneeID(userService.getCurrentLoggedUser().getUuid());
        issueRepository.save(updated);
    }

    @Override
    public void deleteUserToIssueAssignment(Integer issueID) {

        Issue updated = issueRepository.findById(issueID)
                .orElseThrow(() -> new RuntimeException(ISSUE_DOES_NOT_EXISTS));
        updated.setAssigneeID(null);
        issueRepository.save(updated);
    }

    @Override
    public void changeIssueStatus(Integer issueID, Integer statusID) {

        Issue updated = issueRepository
                .findById(ofNullable(issueID).orElseThrow(NullIdentifierException::new))
                .orElseThrow(() -> new IllegalArgumentException(ISSUE_DOES_NOT_EXISTS));

        IssueStatus currentStatus = updated.getIssueStatus();
        IssueStatus newStatus = issueStatusRepository.findById(ofNullable(statusID).orElseThrow(NullIdentifierException::new))
                .orElseThrow(() -> new IllegalArgumentException(STATUS_DOES_NOT_EXISTS));

        if (!isStatusChangeFlowCorrect(currentStatus, newStatus)) {
            throw new RuntimeException(STATUS_FLOW_ORDER_VIOLATION);
        }
        updated.setIssueStatus(newStatus);
        issueRepository.save(updated);
    }

    private boolean isStatusChangeFlowCorrect(IssueStatus current, IssueStatus changeRequested) {

        boolean isIssueDone = DONE.getStatusID().equals(current.getId());
        boolean isNaturalFlow = current.getId().equals(changeRequested.getId()-1);
        boolean isBackFromCodeReviewToInProgress = CODE_REVIEW.getStatusID().equals(current.getId()) && IN_PROGRESS.getStatusID().equals(changeRequested.getId());
        boolean isBackFromTestsToInProgress = IN_TESTS.getStatusID().equals(current.getId()) && IN_PROGRESS.getStatusID().equals(changeRequested.getId());

        if (isIssueDone) {
            return false;
        }
        else return isNaturalFlow || isBackFromCodeReviewToInProgress || isBackFromTestsToInProgress;
    }
}
