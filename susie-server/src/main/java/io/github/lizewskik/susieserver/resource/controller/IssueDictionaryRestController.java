package io.github.lizewskik.susieserver.resource.controller;

import io.github.lizewskik.susieserver.resource.domain.IssuePriority;
import io.github.lizewskik.susieserver.resource.domain.IssueStatus;
import io.github.lizewskik.susieserver.resource.domain.IssueType;
import io.github.lizewskik.susieserver.resource.service.IssuePriorityService;
import io.github.lizewskik.susieserver.resource.service.IssueStatusService;
import io.github.lizewskik.susieserver.resource.service.IssueTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary")
@RequiredArgsConstructor
public class IssueDictionaryRestController {

    private final IssueTypeService issueTypeService;
    private final IssueStatusService issueStatusService;
    private final IssuePriorityService issuePriorityService;

    @GetMapping("/type")
    public List<IssueType> getAllIssueTypes() {
        return issueTypeService.getAllIssueTypes();
    }

    @GetMapping("/status")
    public List<IssueStatus> getAllIssueStatuses() {
        return issueStatusService.getAllIssueStatuses();
    }

    @GetMapping("/priority")
    public List<IssuePriority> getAllIssuePriorities() {
        return issuePriorityService.getAllIssuePriorities();
    }
}
