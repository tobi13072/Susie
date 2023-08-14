package io.github.lizewskik.susieserver.resource.service;

import io.github.lizewskik.susieserver.resource.dto.UserDTO;

public interface UserService {

    UserDTO getCurrentLoggedUser();
    UserDTO getUserByEmail(String email);
    UserDTO getUserByUUID(String uuid);
}
