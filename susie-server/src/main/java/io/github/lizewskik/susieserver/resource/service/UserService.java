package io.github.lizewskik.susieserver.resource.service;

public interface UserService {

    String getCurrentLoggedUserUUID();
    String getUserUUIDByEmail(String email);
}
