package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.dto.CommitmentRuleDTO;

import java.util.Set;

public interface CommitmentRuleService {

    Set<CommitmentRuleDTO> getAllRules(Integer projectID);
    CommitmentRuleDTO defineRule(Integer projectID, String rule);
    CommitmentRuleDTO updateRule(Integer ruleID, String rule);
    void deleteRule(Integer projectID, Integer ruleID);
    void deleteAllRules(Integer projectID);
}
