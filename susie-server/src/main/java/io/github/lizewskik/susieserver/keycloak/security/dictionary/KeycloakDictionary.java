package io.github.lizewskik.susieserver.keycloak.security.dictionary;

public interface KeycloakDictionary {

    String EMPTY_WORD = "";
    String URI_SEPARATOR = "/";
    String USER_DELETION_INFO = "User was successfully deleted from service";
    String USER_SEARCH_FAILED_INFO = "The error occurs, we cannot find given user";
    String SM_ACCOUNT_DELETION_FAILED = "You are assigned as owner to existing project. You cannot delete your account";
    String SM_PERMISSION_ADDED_WARNING = "There can exist only one user with Scrum Master permission in project";
    String PO_PERMISSION_ALREADY_EXISTS = "Product Owner permission is already assigned to one of the project collaborators";
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
    String CLIENT_SECRET_REQUEST_PARAMETER = "client_secret";
    String CLIENT_ID_REQUEST_PARAMETER = "client_id";
    String GRANT_TYPE_REQUEST_PARAMETER = "grant_type";
    String ACCESS_TOKEN_PARAMETER = "access_token";
    String ACCESS_TOKEN_EXPIRES_IN_PARAMETER = "expires_in";
    String REFRESH_TOKEN_REQUEST_PARAMETER = "refresh_token";
    String REFRESH_TOKEN_EXPIRES_IN_PARAMETER = "refresh_expires_in";
    String DEV_PERMISSION = "hasRole('dev')";
    String SM_PERMISSION = "hasRole('sm')";
    String PO_PERMISSION = "hasRole('po')";
    String USER_PERMISSION = "hasRole('client_user')";
    String EMPTY_USER_UUID = null;
    String EMPTY_USER_EMAIL = null;
    String EMPTY_USER_FIRSTNAME = "Unknown";
    String EMPTY_USER_LASTNAME = "Unknown";
}
