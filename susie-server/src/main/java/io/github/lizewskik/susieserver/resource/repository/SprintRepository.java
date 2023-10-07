package io.github.lizewskik.susieserver.resource.repository;

import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.domain.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Integer> {

    Optional<List<Sprint>> findAllByActiveAndProject(Boolean active, Project project);
    Optional<Sprint> findByActiveAndProject(Boolean active, Project project);
    boolean existsByNameAndProject(String name, Project project);
}
