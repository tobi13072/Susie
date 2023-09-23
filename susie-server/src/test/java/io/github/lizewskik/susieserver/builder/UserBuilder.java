package io.github.lizewskik.susieserver.builder;

import io.github.lizewskik.susieserver.resource.dto.UserDTO;

import java.util.UUID;

public class UserBuilder {

    public static final String CURRENT_USER_FIRSTNAME = "TestName";
    public static final String CURRENT_USER_LASTNAME = "TestSurname";
    public static final String CURRENT_USER_EMAIL = "test@susie.com";
    public static final String RANDOM_CURRENT_USER_UUID = UUID.randomUUID().toString();
    public static final String CURRENT_USER_UUID = "be65431c-5951-11ee-8c99-0242ac120002";

    public static UserDTO createCurrentLoggedInUser() {
        return UserDTO.builder()
                .firstName(CURRENT_USER_FIRSTNAME)
                .lastName(CURRENT_USER_LASTNAME)
                .email(CURRENT_USER_EMAIL)
                .uuid(CURRENT_USER_UUID)
                .build();
    }
}
