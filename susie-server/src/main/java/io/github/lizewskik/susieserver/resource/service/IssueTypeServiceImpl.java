package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.IssueType;
import io.github.lizewskik.susieserver.resource.repository.IssueTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueTypeServiceImpl implements IssueTypeService {

    private final IssueTypeRepository issueTypeRepository;

    @Override
    public List<IssueType> getAllIssueTypes() {
        return issueTypeRepository.findAll();
    }
}
