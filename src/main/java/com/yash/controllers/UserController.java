package com.yash.controllers;

import com.yash.model.User;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/profile")
    private ResponseEntity<User>findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }
}
