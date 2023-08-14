package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.Backlog;
import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.dto.IssueDTO;
import io.github.lizewskik.susieserver.resource.dto.IssueGeneralDTO;
import io.github.lizewskik.susieserver.resource.repository.IssueRepository;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService{

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final UserService userService;

    @Override
    public Issue createIssue(IssueDTO issueDTO) {

        if (projectRepository.findById(issueDTO.getProjectID()).isEmpty()) {
            throw new RuntimeException();
        } else {
            Backlog backlog = projectRepository.findById(issueDTO.getProjectID()).get().getBacklog();
            Issue issue = Issue.builder()
                    .name(issueDTO.getName())
                    .description(issueDTO.getDescription())
                    .estimation(issueDTO.getEstimation())
                    .reporterID(userService.getCurrentLoggedUser().getUuid())
                    .backlog(backlog)
                    .build();
            issueRepository.save(issue);
            return issue;
        }
    }

    @Override
    public Issue updateIssue(IssueDTO issueDTO) {

        if (issueRepository.existsById(issueDTO.getIssueID())) {
            throw new RuntimeException();
        } else {
            Issue issue = issueRepository.getReferenceById(issueDTO.getIssueID());
            issue.setName(issueDTO.getName());
            issue.setDescription(issueDTO.getDescription());
            issue.setEstimation(issueDTO.getEstimation());
            issueRepository.save(issue);
            return issue;
        }
    }

    @Override
    public void deleteIssue(Integer issueID) {
        issueRepository.deleteById(issueID);
    }

    @Override
    public Issue getIssueDetails(Integer issueID) {

        if (!issueRepository.existsById(issueID)) {
            throw new RuntimeException();
        }
        return issueRepository.getReferenceById(issueID);
    }

    @Override
    public IssueGeneralDTO getIssuesGeneral(Integer issueID) {

        if (!issueRepository.existsById(issueID)) {
            throw new RuntimeException();
        }
        Issue issue = issueRepository.getReferenceById(issueID);
        return IssueGeneralDTO.builder()
                .name(issue.getName())
                .build();
    }
}
