package com.field.muzi.domain.base;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ( null == authentication || !authentication.isAuthenticated() || authentication.getName() == "anonymousUser") {
            return Optional.empty();
        }

//        System.out.println("확인!!!!! " + authentication.getAuthorities());

        User user = (User) authentication.getPrincipal();
        return Optional.of(user.getUsername());

//        return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
