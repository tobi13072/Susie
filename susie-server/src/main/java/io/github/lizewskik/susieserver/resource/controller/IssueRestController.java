package io.github.lizewskik.susieserver.resource.controller;

import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.dto.IssueDTO;
import io.github.lizewskik.susieserver.resource.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issue")
@RequiredArgsConstructor
public class IssueRestController {

    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<Issue> createIssue(@RequestBody IssueDTO issueDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(issueDTO));
    }

    @PutMapping
    public ResponseEntity<Issue> updateIssue(@RequestBody IssueDTO issueDTO) {
        return ResponseEntity.ok(issueService.updateIssue(issueDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable Integer id){
        issueService.deleteIssue(id);
    }
}
