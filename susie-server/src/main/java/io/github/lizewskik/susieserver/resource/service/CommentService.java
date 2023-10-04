package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.dto.CommentDTO;
import io.github.lizewskik.susieserver.resource.dto.request.CommentMRO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getAllCommentsForIssueID(Integer issueID);
    CommentDTO addCommentToIssue(CommentMRO comment);
    CommentDTO updateComment(CommentMRO comment);
    void deleteComment(Integer commentID);
}
