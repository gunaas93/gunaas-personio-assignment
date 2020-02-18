package com.gunaas.rest.auth;


import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;

public interface AuthenticationFacade {
    @NotNull
    UserDetails currentUser();
}
