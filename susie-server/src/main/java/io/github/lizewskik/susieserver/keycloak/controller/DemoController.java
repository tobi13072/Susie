package io.github.lizewskik.susieserver.keycloak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
@RequiredArgsConstructor
public class DemoController {

    @GetMapping
    @PreAuthorize("hasRole('client_user')")
    public String hello() {
        return "Hello from Spring Boot & Keycloak application";
    }

    @GetMapping("/hello-admin")
    @PreAuthorize("hasRole('client_admin')")
    public String helloAdmin() {
        return "Hello from Spring Boot & Keycloak application - ADMIN";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('client_admin', 'client_user')")
    public String helloAll() {
        return "Hello all!";
    }
}
