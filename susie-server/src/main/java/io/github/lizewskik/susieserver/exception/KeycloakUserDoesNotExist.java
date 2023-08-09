package io.github.lizewskik.susieserver.exception;

public class KeycloakUserDoesNotExist extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Keycloak user with given email does not exist!";

    public KeycloakUserDoesNotExist() {
        super(EXCEPTION_MESSAGE);
    }
}
