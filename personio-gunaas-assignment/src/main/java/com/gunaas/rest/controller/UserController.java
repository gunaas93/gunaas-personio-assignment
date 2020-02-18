package com.gunaas.rest.controller;

import com.gunaas.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("request")
@RequestMapping(
        produces = {"application/json"},
        value = {"/users"}
)
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @PostMapping({"/seed"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> seed() {
        userService.seedUser();
        return new ResponseEntity<>("User seeded", HttpStatus.CREATED);
    }
}
