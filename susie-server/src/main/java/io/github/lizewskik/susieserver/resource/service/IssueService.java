package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.dto.IssueDTO;
import io.github.lizewskik.susieserver.resource.dto.IssueGeneralDTO;
import io.github.lizewskik.susieserver.resource.dto.request.IssueCreationRequest;

import java.util.List;

public interface IssueService {

    Issue createIssue(IssueCreationRequest issue);
    Issue updateIssue(IssueDTO issue);
    void deleteIssue(Integer issueID);
    IssueDTO getIssueDetails(Integer issueID);
    List<IssueGeneralDTO> getIssuesGeneral(Integer projectID);
    void assignCurrentUserToIssue(Integer issueID);
    void deleteUserToIssueAssignment(Integer issueID);
}
