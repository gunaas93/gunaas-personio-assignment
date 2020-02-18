package com.gunaas.rest.models;

import org.springframework.lang.Nullable;

public class Login {
    @Nullable
    private final String name;

    @Nullable
    private final String password;

    public Login(@Nullable String name, @Nullable String password) {
        this.password = password;
        this.name = name;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getPassword() {
        return this.password;
    }
}
