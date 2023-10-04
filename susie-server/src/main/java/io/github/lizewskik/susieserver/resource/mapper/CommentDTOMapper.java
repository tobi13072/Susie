package io.github.lizewskik.susieserver.resource.mapper;

import io.github.lizewskik.susieserver.resource.domain.Comment;
import io.github.lizewskik.susieserver.resource.dto.CommentDTO;
import io.github.lizewskik.susieserver.resource.dto.UserDTO;
import io.github.lizewskik.susieserver.resource.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDTOMapper {

    private final UserService userService;

    public CommentDTO map(Comment from, UserDTO userDTO) {
        return CommentDTO.builder()
                .commentID(from.getId())
                .body(from.getBody())
                .author(userDTO)
                .build();
    }

    public CommentDTO mapV2(Comment from) {
        return CommentDTO.builder()
                .commentID(from.getId())
                .body(from.getBody())
                .author(userService.getUserByUUID(from.getCreatedBy()))
                .build();
    }
}
