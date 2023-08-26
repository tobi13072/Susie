package io.github.lizewskik.susieserver.exception.dictionary;

public interface ExceptionMessages {

    String ISSUE_DOES_NOT_EXISTS = "Issue with given ID does not exists";
    String PROJECT_DOES_NOT_EXISTS = "Project with given ID does not exists";
    String PROJECT_NAME_NOT_UNIQUE = "The project name is not unique within the scope of the property";
    String ACTION_NOT_ALLOWED = "Current user is not allowed to perform this action";
    String KEYCLOAK_USER_DOES_NOT_EXISTS = "Keycloak user with given email or UUID does not exist";
    String STATUS_DOES_NOT_EXISTS = "Issue status with given ID does not exists";
    String ISSUE_ALREADY_HAS_SPRINT = "Given issue is assigned to another Sprint";
    String SPRINT_DOES_NOT_EXISTS = "Spring with given ID does not exists";
    String ISSUE_TYPE_DOES_NOT_EXISTS = "Issue type with given ID does not exists";
    String ISSUE_PRIORITY_DOES_NOT_EXISTS = "Issue priority with given ID does not exists";
    String NULL_IDENTIFIER = "Identifier cannot be null value";
    String STATUS_FLOW_ORDER_VIOLATION = "The principles of proper status flow violation";
    String SPRINT_START_DATE_IN_THE_FUTURE = "The start sprint date is in the future. It is not allowed to start sprint now";
    String SPRINT_ALREADY_STARTED = "Given Sprint is lasting at this point of time";
    String SPRINT_NOT_ACTIVE = "Given sprint is not active";
    String ACTIVE_SPRINT_EXISTS = "There is already exists active sprint";
    String IMPOSSIBLE_ISSUE_STATUS_CHANGE_SPRINT_EMPTY = "Status change is impossible because issue is not within active sprint";
    String IMPOSSIBLE_ISSUE_STATUS_CHANGE_SPRINT_NOT_ACTIVE = "Status change is impossible because issue does not belong to active sprint";
    String EMPTY_SPRINT = "It is not allowed to start sprint without any issue";
}
