package org.example.authentification.auditing;

import org.example.authentification.User.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<String> {

    @Override

    public Optional<String> getCurrentAuditor() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                   authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }
        User userPrincipal = (User) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getFirstname()+" "+userPrincipal.getLastname());

    }

}
