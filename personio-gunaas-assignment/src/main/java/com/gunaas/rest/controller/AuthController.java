package com.gunaas.rest.controller;

import com.gunaas.rest.data.LoginDto;
import com.gunaas.rest.entity.UserEntity;
import com.gunaas.rest.models.Login;
import com.gunaas.rest.service.JwtService;
import com.gunaas.rest.service.UserService;
import com.nimbusds.jose.JOSEException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("request")
@RequestMapping(
        produces = {"application/json"},
        value = {"/auth"}
)
public class AuthController extends BaseController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    public static final String LOGIN_SUCCESS = "Login success";
    public static final String LOGIN_FAIL = "Invalid username or password";

    @PostMapping
    @NotNull
    public ResponseEntity<LoginDto> login(@RequestBody @NotNull Login login) throws JOSEException {
        LoginDto dto = new LoginDto();
        HttpStatus httpStatus = null;
        UserEntity user = userService.checkLoginAndGetUser(login);
        if (user != null) {
            dto.setAccessToken(jwtService.generateLoginToken(user.getId()));
            dto.setMessage(LOGIN_SUCCESS);
            httpStatus = HttpStatus.OK;
        } else {
            dto.setMessage(LOGIN_FAIL);
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(dto, httpStatus);
    }
}
