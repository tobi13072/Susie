package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.IssueStatus;
import io.github.lizewskik.susieserver.resource.repository.IssueStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueStatusServiceImpl implements IssueStatusService {

    private final IssueStatusRepository issueStatusRepository;

    @Override
    public List<IssueStatus> getAllIssueStatuses() {
        return issueStatusRepository.findAll();
    }
}
