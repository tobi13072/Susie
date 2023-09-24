package io.github.lizewskik.susieserver.builder;

import io.github.lizewskik.susieserver.resource.domain.IssuePriority;
import io.github.lizewskik.susieserver.resource.domain.IssueStatus;
import io.github.lizewskik.susieserver.resource.domain.IssueType;
import io.github.lizewskik.susieserver.resource.domain.dictionary.IssuePriorityEnum;
import io.github.lizewskik.susieserver.resource.domain.dictionary.IssuePriorityID;
import io.github.lizewskik.susieserver.resource.domain.dictionary.IssueStatusEnum;
import io.github.lizewskik.susieserver.resource.domain.dictionary.IssueStatusID;
import io.github.lizewskik.susieserver.resource.domain.dictionary.IssueTypeEnum;
import io.github.lizewskik.susieserver.resource.domain.dictionary.IssueTypeID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DictionaryBuilder {

    public static IssueType createIssueType(IssueTypeEnum type, Integer typeID) {
        IssueType issueType = new IssueType();
        issueType.setId(typeID);
        issueType.setType(type);
        return issueType;
    }

    public static IssuePriority createIssuePriority(IssuePriorityEnum priority, Integer priorityID) {
        IssuePriority issuePriority = new IssuePriority();
        issuePriority.setId(priorityID);
        issuePriority.setPriority(priority);
        return issuePriority;
    }

    public static IssueStatus createIssueStatus(IssueStatusEnum status, Integer statusID) {
        IssueStatus issueStatus = new IssueStatus();
        issueStatus.setStatusName(status);
        issueStatus.setId(statusID);
        return issueStatus;
    }

    public static List<IssueType> createAllIssueTypes() {
        List<IssueType> allTypes = new ArrayList<>();
        List<IssueTypeID> issueTypeIDS = Arrays.stream(IssueTypeID.values()).toList();
        Arrays.stream(IssueTypeEnum.values()).toList().forEach(type -> {
            Integer issueTypeID = issueTypeIDS.stream().filter(typeID -> typeID.name().equals(type.name())).toList().get(0).getTypeID();
            allTypes.add(createIssueType(type, issueTypeID));
        });
        return allTypes;
    }

    public static List<IssueStatus> createAllIssueStatuses() {
        List<IssueStatus> allStatuses = new ArrayList<>();
        List<IssueStatusID> issueStatusIDs = Arrays.stream(IssueStatusID.values()).toList();
        Arrays.stream(IssueStatusEnum.values()).toList().forEach(status -> {
            Integer issueStatusID = issueStatusIDs.stream().filter(statusID -> statusID.name().equals(status.name())).toList().get(0).getStatusID();
            allStatuses.add(createIssueStatus(status, issueStatusID));
        });
        return allStatuses;
    }

    public static List<IssuePriority> createAllIssuePriorities() {
        List<IssuePriority> allPriorities = new ArrayList<>();
        List<IssuePriorityID> allPriorityIDs = Arrays.stream(IssuePriorityID.values()).toList();
        Arrays.stream(IssuePriorityEnum.values()).toList().forEach(priority -> {
            Integer issuePriorityID = allPriorityIDs.stream().filter(priorityID -> priorityID.name().equals(priority.name())).toList().get(0).getPriorityID();
            allPriorities.add(createIssuePriority(priority, issuePriorityID));
        });
        return allPriorities;
    }
}
