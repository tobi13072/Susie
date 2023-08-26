package io.github.lizewskik.susieserver.resource.controller;

import io.github.lizewskik.susieserver.resource.dto.SprintDTO;
import io.github.lizewskik.susieserver.resource.dto.request.SprintCreationRequest;
import io.github.lizewskik.susieserver.resource.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sprint")
@RequiredArgsConstructor
public class SprintRestController {

    private final SprintService sprintService;

    @GetMapping("/active")
    public ResponseEntity<SprintDTO> getActiveSprint() {
        return ResponseEntity.ok(sprintService.getActiveSprint());
    }

    @GetMapping("/non-activated")
    public ResponseEntity<List<SprintDTO>> getAllSprints() {
        return ResponseEntity.ok(sprintService.getAllNonActivatedSprints());
    }

    @PostMapping
    public ResponseEntity<SprintDTO> createSprint(@RequestBody SprintCreationRequest sprintDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sprintService.createSprint(sprintDTO));
    }

    @PostMapping("/{sprintID}/issue/{issueID}")
    public void addIssueToSprint(@PathVariable Integer sprintID, @PathVariable Integer issueID) {
        sprintService.addIssueToSprint(issueID, sprintID);
    }

    @PatchMapping("/start/{id}")
    public void startSprint(@PathVariable Integer id) {
        sprintService.startSprint(id);
    }

    @PatchMapping("/stop/{id}")
    public void stopSprint(@PathVariable Integer id) {
        sprintService.stopSprint(id);
    }
}
