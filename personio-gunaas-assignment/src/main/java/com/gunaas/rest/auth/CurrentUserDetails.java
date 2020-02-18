package com.gunaas.rest.auth;

import com.gunaas.rest.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

public final class CurrentUserDetails implements UserDetails {
    private String userName;
    private String password;
    private HashSet authorities;

    @NotNull
    public Collection getAuthorities() {
        return this.authorities;
    }

    public boolean isEnabled() {
        return true;
    }

    @NotNull
    public String getUsername() {
        return this.userName;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    @NotNull
    public String getPassword() {
        return this.password;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public CurrentUserDetails(@NotNull UserEntity user) {
        super();
        this.userName = user.getUserName();
        this.password = user.getEncryptedPassword();
        this.authorities = new HashSet();
    }
}
