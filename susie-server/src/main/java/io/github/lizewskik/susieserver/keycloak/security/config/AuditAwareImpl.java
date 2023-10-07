package io.github.lizewskik.susieserver.keycloak.security.config;

import io.github.lizewskik.susieserver.resource.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<String> {

    private final UserService userService;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(userService.getCurrentLoggedUser().getUuid());
    }
}
