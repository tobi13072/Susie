package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.dto.IssueDTO;

public interface IssueService {

    Issue createIssue(IssueDTO issue, Integer projectID);
    Issue updateIssue(IssueDTO issue, Integer projectID);
    void deleteIssue(Integer issueID);
    Issue getIssueDetails(Integer issueID);
    Issue getIssuesGeneral(Integer issueID);
}
