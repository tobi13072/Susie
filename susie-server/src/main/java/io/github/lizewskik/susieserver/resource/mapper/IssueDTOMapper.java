package io.github.lizewskik.susieserver.resource.mapper;

import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.dto.IssueDTO;
import io.github.lizewskik.susieserver.resource.service.CommentService;
import io.github.lizewskik.susieserver.resource.service.UserService;
import io.github.lizewskik.susieserver.utils.collection.CollectionsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class IssueDTOMapper {

    private final UserService userService;
    private final CommentService commentService;

    public IssueDTO map(Issue from) {
        return IssueDTO.builder()
                .issueID(from.getId())
                .name(from.getName())
                .description(from.getDescription())
                .estimation(from.getEstimation())
                .reporter(userService.getUserByUUID(from.getReporterID()))
                .projectID(from.getProjectID())
                .sprintID(
                        isNull(from.getSprint()) ? null : from.getSprint().getId()
                )
                .assignee(
                        isNull(from.getAssigneeID()) ? null : userService.getUserByUUID(from.getAssigneeID())
                )
                .issueTypeID(
                        isNull(from.getIssueType()) ? null : from.getIssueType().getId()
                )
                .issuePriorityID(
                        isNull(from.getIssuePriority()) ? null : from.getIssuePriority().getId()
                )
                .issueStatusID(
                        isNull(from.getIssueStatus()) ? null : from.getIssueStatus().getId()
                )
                .comments(
                        CollectionsUtils.isNullOrEmpty(from.getComments()) ? new ArrayList<>() : commentService.getAllCommentsForIssueID(from.getId())
                )
                .build();
    }
}
