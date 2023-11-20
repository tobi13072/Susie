package io.github.lizewskik.susieserver.resource.repository;

import io.github.lizewskik.susieserver.resource.domain.CommitmentRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitmentRuleRepository extends JpaRepository<CommitmentRule, Integer> {

}
