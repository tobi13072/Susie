package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.dto.UserDTO;

import java.util.Set;

public interface UserService {

    UserDTO getCurrentLoggedUser();
    UserDTO getUserByEmail(String email);
    UserDTO getUserByUUID(String uuid);
    boolean isProjectOwner(Integer projectID);
    Set<String> getAllProjectUsersUUIDs(Integer projectID);
}
