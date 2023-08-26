package io.github.lizewskik.susieserver.resource.repository;

import io.github.lizewskik.susieserver.resource.domain.Issue;
import io.github.lizewskik.susieserver.resource.domain.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

    List<Issue> findAllBySprint(Sprint sprint);
}
