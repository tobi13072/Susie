package io.github.lizewskik.susieserver.keycloak.security.utils.builder;

import org.keycloak.OAuth2Constants;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public class UserRepresentationBuilder {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Boolean enabled;
    private Boolean emailVerified;

    public static UserRepresentationBuilder builder() {
        return new UserRepresentationBuilder();
    }

    public UserRepresentation build() {

        CredentialRepresentation credentials = CredentialsRepresentationBuilder.builder()
                .type(OAuth2Constants.PASSWORD)
                .temporary(Boolean.FALSE)
                .value(password)
                .build();

        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCredentials(List.of(credentials));
        user.setEnabled(enabled);
        user.setEmailVerified(emailVerified);
        return user;
    }

    public UserRepresentationBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserRepresentationBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserRepresentationBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserRepresentationBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserRepresentationBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserRepresentationBuilder enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public UserRepresentationBuilder emailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
        return this;
    }
}
