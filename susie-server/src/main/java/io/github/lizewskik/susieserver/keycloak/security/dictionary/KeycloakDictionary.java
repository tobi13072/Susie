package io.github.lizewskik.susieserver.keycloak.security.dictionary;

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
    String KEYCLOAK_FIRSTNAME_TOKEN_CLAIM = "given_name";
    String KEYCLOAK_LASTNAME_TOKEN_CLAIM = "family_name";
    String DEV_PERMISSION = "hasRole('dev')";
    String SM_PERMISSION = "hasRole('sm')";
    String PO_PERMISSION = "hasRole('po')";
    String USER_PERMISSION = "hasRole('client_user')";
}
