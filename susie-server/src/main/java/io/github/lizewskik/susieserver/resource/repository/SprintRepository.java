package io.github.lizewskik.susieserver.resource.repository;

import io.github.lizewskik.susieserver.resource.domain.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Integer> {

    Optional<Sprint> findByActive(Boolean active);
}
