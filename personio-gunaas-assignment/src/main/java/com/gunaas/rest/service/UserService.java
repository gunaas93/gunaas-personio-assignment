package com.gunaas.rest.service;

import com.gunaas.rest.entity.UserEntity;
import com.gunaas.rest.models.Login;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public interface UserService {
    @Nullable
    UserEntity checkLoginAndGetUser(@NotNull Login login);

    @Nullable
    UserEntity getUserById(@Nullable Long userId);

    void seedUser();
}
