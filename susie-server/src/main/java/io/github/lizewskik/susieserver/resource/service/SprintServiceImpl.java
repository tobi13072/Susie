package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.exception.definition.NullIdentifierException;
import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.domain.Sprint;
import io.github.lizewskik.susieserver.resource.dto.SprintDTO;
import io.github.lizewskik.susieserver.resource.mapper.SprintDTOMapper;
import io.github.lizewskik.susieserver.resource.repository.IssueRepository;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import io.github.lizewskik.susieserver.resource.repository.SprintRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ACTIVE_SPRINT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.EMPTY_SPRINT;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_ALREADY_HAS_SPRINT;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.ISSUE_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.PROJECT_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_ALREADY_STARTED;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_DOES_NOT_EXISTS;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_NAME_NOT_UNIQUE;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_NOT_ACTIVE;
import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.SPRINT_START_DATE_IN_THE_FUTURE;
import static io.github.lizewskik.susieserver.utils.DateUtils.reduceTimeFromZonedDateTime;
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
    public SprintDTO getActiveSprint(Integer projectID) {

        Project project = projectRepository.findById(projectID)
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));

        if (sprintRepository.findByActiveAndProject(TRUE, project).isPresent()) {
            return sprintDTOMapper.map(sprintRepository.findByActiveAndProject(TRUE, project).get());
        }
        return null;
    }

    @Override
    public List<SprintDTO> getAllNonActivatedSprints(Integer projectID) {

        Project project = projectRepository.findById(projectID)
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));

        if (sprintRepository.findAllByActiveAndProject(FALSE, project).isEmpty()) {
            return new ArrayList<>();
        }

        return sprintRepository.findAllByActiveAndProject(FALSE, project)
                .get()
                .stream()
                .map(sprintDTOMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public SprintDTO createSprint(SprintDTO sprintDTO) {

        Project project = projectRepository.findById(sprintDTO.getProjectID())
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));

        if (sprintRepository.existsByNameAndProject(sprintDTO.getName(), project)) {
            throw new RuntimeException(SPRINT_NAME_NOT_UNIQUE);
        }

        Sprint sprint = Sprint.builder()
                .name(sprintDTO.getName())
                .startDate(sprintDTO.getStartTime())
                .sprintIssues(new HashSet<>())
                .active(FALSE)
                .project(project)
                .build();
        sprintRepository.save(sprint);
        return sprintDTOMapper.map(sprint);
    }

    @Override
    public void deleteSprint(Integer sprintID) {

        Sprint sprintToBeDeleted = sprintRepository.findById(sprintID)
                .orElseThrow(() -> new RuntimeException(SPRINT_DOES_NOT_EXISTS));

        if (!sprintToBeDeleted.getSprintIssues().isEmpty()) {
            List<Issue> issues = issueRepository.findAllBySprint(sprintToBeDeleted)
                    .stream()
                    .peek(sprint -> sprint.setSprint(null))
                    .toList();
            issueRepository.saveAll(issues);
        }

        sprintToBeDeleted.setSprintIssues(new HashSet<>());
        sprintRepository.save(sprintToBeDeleted);

        sprintRepository.deleteById(sprintID);
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

        Set<Issue> sprintIssues;
        if (isNull(sprint.getSprintIssues())) {
            sprintIssues = new HashSet<>(List.of(issue));
        } else {
            sprintIssues = sprint.getSprintIssues();
            sprintIssues.add(issue);
        }
        sprint.setSprintIssues(sprintIssues);
        sprintRepository.save(sprint);

        issue.setSprint(sprint);
        issueRepository.save(issue);
    }

    @Override
    public void deleteIssueFromSprint(Integer sprintID, Integer issueID) {

        Sprint sprint = sprintRepository.findById(sprintID)
                .orElseThrow(() -> new RuntimeException(SPRINT_DOES_NOT_EXISTS));
        Issue issue = issueRepository.findById(issueID)
                .orElseThrow(() -> new RuntimeException(ISSUE_DOES_NOT_EXISTS));

        Set<Issue> sprintIssues = sprint.getSprintIssues();
        sprintIssues.remove(issue);
        sprint.setSprintIssues(sprintIssues);
        sprintRepository.save(sprint);

        issue.setSprint(null);
        issueRepository.save(issue);
    }

    @Override
    public void startSprint(Integer sprintID) {

        Sprint updated = sprintRepository.findById(ofNullable(sprintID).orElseThrow(NullIdentifierException::new))
                .orElseThrow(() -> new IllegalArgumentException(SPRINT_DOES_NOT_EXISTS));

        if(sprintRepository.findByActiveAndProject(TRUE, updated.getProject()).isPresent()) {
            throw new RuntimeException(ACTIVE_SPRINT_EXISTS);
        }

        if (updated.getSprintIssues().isEmpty()) {
            throw new RuntimeException(EMPTY_SPRINT);
        }

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

        ZonedDateTime currentSystemDate = reduceTimeFromZonedDateTime(ZonedDateTime.now());
        Optional<ZonedDateTime> startSprintDate = ofNullable(sprint.getStartDate());
        boolean isSprintActive = sprint.getActive();

        if (isSprintActive) {
            return Pair.of(FALSE, SPRINT_ALREADY_STARTED);
        }

        if (startSprintDate.isEmpty()) {
            return Pair.of(TRUE, DATES_VALID);
        }

        ZonedDateTime startSprintDateReduced = reduceTimeFromZonedDateTime(startSprintDate.get());
        boolean isLaterThanCurrentSystemDate = startSprintDateReduced.isAfter(currentSystemDate);
        if (isLaterThanCurrentSystemDate) {
            return Pair.of(FALSE, SPRINT_START_DATE_IN_THE_FUTURE);
        }

        return Pair.of(TRUE, DATES_VALID);
    }
}
