package io.github.lizewskik.susieserver.security.dictionary;

public interface KeycloakDictionary {

    String EMPTY_WORD = "";
    String URI_SEPARATOR = "/";
    String KEYCLOAK_CLIENT_ROLE_USER = "client_user";
    String KEYCLOAK_CLIENT_ROLE_ADMIN = "client_admin";
    String KEYCLOAK_CLIENT_ROLE_PRODUCT_OWNER = "po";
    String KEYCLOAK_CLIENT_ROLE_SCRUM_MASTER = "sm";
    String KEYCLOAK_CLIENT_ROLE_DEVELOPER = "dev";
    String KEYCLOAK_URI_ADMIN_PARAMETER = "admin";
    String KEYCLOAK_URI_USERS_PARAMETER = "users";
    String KEYCLOAK_URI_REALMS_PARAMETER = "realms";
    String KEYCLOAK_URI_SECRET_PARAMETER = "secret";
}
