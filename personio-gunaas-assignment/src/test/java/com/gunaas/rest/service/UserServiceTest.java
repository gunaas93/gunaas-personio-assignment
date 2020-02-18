package com.gunaas.rest.service;

import com.gunaas.rest.entity.UserEntity;
import com.gunaas.rest.models.Login;
import com.gunaas.rest.repository.UserRepository;
import com.gunaas.rest.service.impl.UserServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService = new UserServiceImpl();
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @NotNull
    public static final String userName = "abc";
    @NotNull
    public static final String password = "123456";


    @Test
    public final void givenUser_whenRightUserAndPassword_thenReturnUser() {
        UserEntity user = new UserEntity("abc", "123456");
        Mockito.when(userRepository.findUserEntityByUserName(userName)).thenReturn(user);
        Mockito.when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        UserEntity currentUser = userService.checkLoginAndGetUser(new Login(userName, password));
        Assertions.assertEquals(user, currentUser);
    }

    @Test
    public final void givenUser_whenRightUserButWrongPassword_thenReturnNull() {
        UserEntity user = new UserEntity(userName, password);
        Mockito.when(userRepository.findUserEntityByUserName(userName)).thenReturn(user);
        Mockito.when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        UserEntity currentUser = userService.checkLoginAndGetUser(new Login(userName, password));
        Assertions.assertNull(currentUser);
    }

    @Test
    public final void givenUser_whenEmptyUserAndPassword_thenReturnNull() {
        UserEntity user1 = userService.checkLoginAndGetUser(new Login("", ""));
        UserEntity user2 = userService.checkLoginAndGetUser(new Login(null, ""));
        UserEntity user3 = userService.checkLoginAndGetUser(new Login("", null));
        UserEntity user4 = userService.checkLoginAndGetUser(new Login(null, null));
        Assertions.assertNull(user1);
        Assertions.assertNull(user2);
        Assertions.assertNull(user3);
        Assertions.assertNull(user4);
    }
}
