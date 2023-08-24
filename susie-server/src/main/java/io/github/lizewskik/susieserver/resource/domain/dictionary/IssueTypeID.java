package io.github.lizewskik.susieserver.resource.domain.dictionary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IssueTypeID {

    USER_STORY(1),
    BUG(2),
    TO_DO(3);

    private final Integer typeID;
}
