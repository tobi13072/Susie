package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.dto.IssueDTO;
import io.github.lizewskik.susieserver.resource.dto.IssueGeneralDTO;

public interface IssueService {

    Issue createIssue(IssueDTO issue);
    Issue updateIssue(IssueDTO issue);
    void deleteIssue(Integer issueID);
    Issue getIssueDetails(Integer issueID);
    IssueGeneralDTO getIssuesGeneral(Integer issueID);
}
