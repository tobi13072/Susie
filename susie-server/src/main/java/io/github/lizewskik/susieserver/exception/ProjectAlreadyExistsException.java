package io.github.lizewskik.susieserver.exception;

public class ProjectAlreadyExistsException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Project with given name and project owner already exists!";

    public ProjectAlreadyExistsException() {
        super(EXCEPTION_MESSAGE);
    }
}
