package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.dto.IssueDTO;
import io.github.lizewskik.susieserver.resource.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService{

    private IssueRepository issueRepository;
    private UserService userService;

    @Override
    public Issue createIssue(IssueDTO issue, Integer projectID) {
        return null;
    }

    @Override
    public Issue updateIssue(IssueDTO issue, Integer projectID) {
        return null;
    }

    @Override
    public void deleteIssue(Integer issueID) {
        issueRepository.deleteById(issueID);
    }

    @Override
    public Issue getIssueDetails(Integer issueID) {
        return null;
    }

    @Override
    public Issue getIssuesGeneral(Integer issueID) {
        return null;
    }
}
