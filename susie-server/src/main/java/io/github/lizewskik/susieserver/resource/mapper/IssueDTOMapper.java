package io.github.lizewskik.susieserver.resource.mapper;

import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.dto.IssueDTO;
import io.github.lizewskik.susieserver.resource.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class IssueDTOMapper {

    private final UserService userService;

    public IssueDTO map(Issue from) {
        return IssueDTO.builder()
                .issueID(from.getId())
                .name(from.getName())
                .description(from.getDescription())
                .estimation(from.getEstimation())
                .reporter(userService.getUserByUUID(from.getReporterID()))
                .assignee(
                        Objects.isNull(from.getAssigneeID()) ? null : userService.getUserByUUID(from.getAssigneeID())
                )
                .projectID(from.getBacklog().getProject().getId())
                .build();
    }
}
