package com.gunaas.rest.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    @NotNull
    public UserDetails currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (CurrentUserDetails) auth.getPrincipal();
    }
}
