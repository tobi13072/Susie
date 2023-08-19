package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.domain.IssueStatus;
import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.domain.dictionary.IssueStatusID;
import io.github.lizewskik.susieserver.resource.dto.IssueDTO;
import io.github.lizewskik.susieserver.resource.dto.IssueGeneralDTO;
import io.github.lizewskik.susieserver.resource.dto.request.IssueCreationRequest;
import io.github.lizewskik.susieserver.resource.mapper.IssueDTOMapper;
import io.github.lizewskik.susieserver.resource.repository.IssueRepository;
import io.github.lizewskik.susieserver.resource.repository.IssueStatusRepository;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.PROJECT_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.STATUS_DOES_NOT_EXISTS;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService{

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final IssueStatusRepository issueStatusRepository;
    private final UserService userService;
    private final IssueDTOMapper issueDTOMapper;

    @Override
    public Issue createIssue(IssueCreationRequest issueDTO) {

        Project project = projectRepository.findById(issueDTO.getProjectID())
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));
        IssueStatus status = issueStatusRepository.findById(IssueStatusID.TO_DO.getStatusID())
                .orElseThrow(() -> new RuntimeException(STATUS_DOES_NOT_EXISTS));
        Issue issue = Issue.builder()
                .name(issueDTO.getName())
                .description(issueDTO.getDescription())
                .estimation(issueDTO.getEstimation())
                .reporterID(userService.getCurrentLoggedUser().getUuid())
                .issueStatus(status)
                .backlog(project.getBacklog())
                .build();
        issueRepository.save(issue);
        return issue;
    }

    @Override
    public Issue updateIssue(IssueDTO issueDTO) {

        Issue updated = issueRepository.findById(issueDTO.getIssueID())
                .orElseThrow(() -> new RuntimeException(ISSUE_DOES_NOT_EXISTS));
        updated.setName(issueDTO.getName());
        updated.setDescription(issueDTO.getDescription());
        updated.setEstimation(issueDTO.getEstimation());
        issueRepository.save(updated);
        return updated;
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
                .map(issue -> IssueGeneralDTO
                        .builder()
                        .id(issue.getId())
                        .name(issue.getName())
                        .assignee(
                                Objects.isNull(issue.getAssigneeID()) ? null : userService.getUserByUUID(issue.getAssigneeID())
                        )
                        .build())
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
}
