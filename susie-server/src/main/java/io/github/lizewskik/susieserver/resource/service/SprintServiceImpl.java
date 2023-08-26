package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.exception.NullIdentifierException;
import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.domain.Sprint;
import io.github.lizewskik.susieserver.resource.dto.SprintDTO;
import io.github.lizewskik.susieserver.resource.dto.request.SprintCreationRequest;
import io.github.lizewskik.susieserver.resource.mapper.SprintDTOMapper;
import io.github.lizewskik.susieserver.resource.repository.IssueRepository;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import io.github.lizewskik.susieserver.resource.repository.SprintRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ACTIVE_SPRINT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_ALREADY_HAS_SPRINT;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.PROJECT_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_ALREADY_STARTED;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_NOT_ACTIVE;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_START_DATE_IN_THE_FUTURE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Service
@Transactional
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {

    private static final String DATES_VALID = "Dates are valid";

    private final SprintRepository sprintRepository;
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final SprintDTOMapper sprintDTOMapper;

    @Override
    public SprintDTO getActiveSprint() {
        if (sprintRepository.findByActive(TRUE).isPresent()) {
            return sprintDTOMapper.map(sprintRepository.findByActive(TRUE).get());
        }
        return null;
    }

    @Override
    public List<SprintDTO> getAllNonActivatedSprints() {
        return sprintRepository.findAll()
                .stream()
                .filter(sprint -> sprint.getActive().equals(FALSE))
                .map(sprintDTOMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public SprintDTO createSprint(SprintCreationRequest sprintDTO) {

        Project project = projectRepository.findById(sprintDTO.getProjectID())
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));
        Sprint sprint = Sprint.builder()
                .name(sprintDTO.getName())
                .startDate(sprintDTO.getStartTime())
                .project(project)
                .build();
        sprintRepository.save(sprint);
        return sprintDTOMapper.map(sprint);
    }

    @Override
    public void addIssueToSprint(Integer issueID, Integer sprintID) {

        Issue issue = issueRepository.findById(issueID)
                .orElseThrow(() -> new RuntimeException(ISSUE_DOES_NOT_EXISTS));
        Sprint sprint = sprintRepository.findById(sprintID)
                .orElseThrow(() -> new RuntimeException(SPRINT_DOES_NOT_EXISTS));
        if (!isNull(issue.getSprint())) {
            throw new RuntimeException(ISSUE_ALREADY_HAS_SPRINT);
        }

        issue.setSprint(sprint);
    }

    @Override
    public void startSprint(Integer sprintID) {

        if(sprintRepository.findByActive(TRUE).isPresent()) {
            throw new RuntimeException(ACTIVE_SPRINT_EXISTS);
        }

        Sprint updated = sprintRepository.findById(ofNullable(sprintID).orElseThrow(NullIdentifierException::new))
                .orElseThrow(() -> new IllegalArgumentException(SPRINT_DOES_NOT_EXISTS));

        Pair<Boolean, String> validationResult = validateSprintDatesForStart(updated);
        if (!validationResult.getKey()) {
            throw new RuntimeException(validationResult.getValue());
        }

        if (isNull(updated.getStartDate())) {
            updated.setStartDate(ZonedDateTime.now());
        }

        updated.setActive(TRUE);
        sprintRepository.save(updated);
    }

    @Override
    public void stopSprint(Integer sprintID) {

        Sprint updated = sprintRepository.findById(ofNullable(sprintID).orElseThrow(NullIdentifierException::new))
                .orElseThrow(() -> new IllegalArgumentException(SPRINT_DOES_NOT_EXISTS));

        if (!updated.getActive()) {
            throw new RuntimeException(SPRINT_NOT_ACTIVE);
        }

        updated.setActive(FALSE);
        sprintRepository.save(updated);
    }

    private Pair<Boolean, String> validateSprintDatesForStart(Sprint sprint) {

        ZonedDateTime currentSystemDate = ZonedDateTime.now();
        Optional<ZonedDateTime> startSprintDate = ofNullable(sprint.getStartDate());
        boolean isSprintActive = sprint.getActive();

        if (isSprintActive) {
            return Pair.of(FALSE, SPRINT_ALREADY_STARTED);
        }

        if (startSprintDate.isEmpty()) {
            return Pair.of(TRUE, DATES_VALID);
        }

        boolean isLaterThanCurrentSystemDate = startSprintDate.get().isAfter(currentSystemDate);
        if (isLaterThanCurrentSystemDate) {
            return Pair.of(FALSE, SPRINT_START_DATE_IN_THE_FUTURE);
        }

        return Pair.of(TRUE, DATES_VALID);
    }
}
