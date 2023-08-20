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
}
