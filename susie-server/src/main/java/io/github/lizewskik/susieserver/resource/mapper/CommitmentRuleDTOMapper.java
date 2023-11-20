package io.github.lizewskik.susieserver.resource.mapper;

import io.github.lizewskik.susieserver.resource.domain.CommitmentRule;
import io.github.lizewskik.susieserver.resource.dto.CommitmentRuleDTO;
import org.springframework.stereotype.Component;

@Component
public class CommitmentRuleDTOMapper {

    public CommitmentRuleDTO map(CommitmentRule from) {
        CommitmentRuleDTO rule = new CommitmentRuleDTO();
        rule.setRuleID(from.getId());
        rule.setRuleName(from.getRule());
        return rule;
    }
}
