package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.dto.UserDTO;

import java.util.Set;

public interface UserService {

    UserDTO getCurrentLoggedUser();
    UserDTO getUserByEmail(String email);
    UserDTO getUserByUUID(String uuid);
    UserDTO getUserSafely(String uuid);
    boolean isProjectOwner(Integer projectID);
    boolean isAnyProjectOwner(String uuid);
    Set<String> getAllProjectUsersUUIDs(Integer projectID);
}
