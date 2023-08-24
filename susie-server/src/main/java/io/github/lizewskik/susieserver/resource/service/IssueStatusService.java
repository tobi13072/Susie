package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.IssueStatus;

import java.util.List;

public interface IssueStatusService {

    List<IssueStatus> getAllIssueStatuses();
}
