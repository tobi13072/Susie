package io.github.lizewskik.susieserver.exception;

import static io.github.lizewskik.susieserver.exception.dictionary.ExceptionMessages.NULL_IDENTIFIER;

public class NullIdentifierException extends IllegalArgumentException {

    public NullIdentifierException() {
        super(NULL_IDENTIFIER);
    }
}
