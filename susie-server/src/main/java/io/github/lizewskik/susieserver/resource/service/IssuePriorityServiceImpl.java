package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.domain.IssuePriority;
import io.github.lizewskik.susieserver.resource.repository.IssuePriorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssuePriorityServiceImpl implements IssuePriorityService {

    private final IssuePriorityRepository issuePriorityRepository;

    @Override
    public List<IssuePriority> getAllIssuePriorities() {
        return issuePriorityRepository.findAll();
    }
}
