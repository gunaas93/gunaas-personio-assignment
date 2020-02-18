package com.gunaas.rest.service.impl;

import com.gunaas.rest.entity.UserEntity;
import com.gunaas.rest.models.Login;
import com.gunaas.rest.repository.UserRepository;
import com.gunaas.rest.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@Scope("prototype")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void seedUser() {
        UserEntity userEntity = new UserEntity("gunaas", passwordEncoder.encode("123"));
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity checkLoginAndGetUser(Login login) {
        if (StringUtils.isBlank(login.getName()) || StringUtils.isBlank(login.getPassword())) {
            return null;
        }
        UserEntity user = userRepository.findUserEntityByUserName(login.getName());
        if (user == null || !passwordEncoder.matches(login.getPassword(), user.getEncryptedPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public UserEntity getUserById(@NotNull Long id) {
        return userRepository.findById(id).get();
    }
}
