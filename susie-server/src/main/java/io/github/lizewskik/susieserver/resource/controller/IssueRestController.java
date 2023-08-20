package io.github.lizewskik.susieserver.resource.controller;

import io.github.lizewskik.susieserver.resource.dto.IssueDTO;
import io.github.lizewskik.susieserver.resource.dto.IssueGeneralDTO;
import io.github.lizewskik.susieserver.resource.dto.request.IssueCreationRequest;
import io.github.lizewskik.susieserver.resource.dto.request.IssueUpdateRequest;
import io.github.lizewskik.susieserver.resource.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/issue")
@RequiredArgsConstructor
public class IssueRestController {

    private final IssueService issueService;

    @GetMapping
    public ResponseEntity<List<IssueGeneralDTO>> getGeneralIssuesInfoByProjectID(@RequestParam Integer projectID) {
        return ResponseEntity.ok(issueService.getIssuesGeneral(projectID));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<IssueDTO> getIssueDetailsByID(@PathVariable Integer id) {
        return ResponseEntity.ok(issueService.getIssueDetails(id));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueCreationRequest issueDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(issueDTO));
    }

    @PutMapping
    public ResponseEntity<IssueDTO> updateIssue(@RequestBody IssueUpdateRequest issueDTO) {
        return ResponseEntity.ok(issueService.updateIssue(issueDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable Integer id){
        issueService.deleteIssue(id);
    }

    @PostMapping("/{id}/assign")
    public void assignCurrentUserToIssue(@PathVariable Integer id) {
        issueService.assignCurrentUserToIssue(id);
    }

    @PutMapping("/{id}/delete-assignment")
    public void deleteUserToIssueAssignment(@PathVariable Integer id) {
        issueService.deleteUserToIssueAssignment(id);
    }
}
