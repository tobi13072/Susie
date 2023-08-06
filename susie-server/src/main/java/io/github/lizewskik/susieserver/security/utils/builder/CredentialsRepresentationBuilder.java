package io.github.lizewskik.susieserver.security.utils.builder;

import org.keycloak.representations.idm.CredentialRepresentation;

public class CredentialsRepresentationBuilder {

    private Boolean temporary;
    private String type;
    private String value;

    public static CredentialsRepresentationBuilder builder() {
        return new CredentialsRepresentationBuilder();
    }

    public CredentialRepresentation build() {
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(type);
        credentials.setTemporary(temporary);
        credentials.setValue(value);
        return credentials;
    }

    public CredentialsRepresentationBuilder temporary(Boolean temporary) {
        this.temporary = temporary;
        return this;
    }

    public CredentialsRepresentationBuilder type(String type) {
        this.type = type;
        return this;
    }

    public CredentialsRepresentationBuilder value(String value) {
        this.value = value;
        return this;
    }
}
