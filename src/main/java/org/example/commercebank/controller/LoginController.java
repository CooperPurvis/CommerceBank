package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.User;
import org.example.commercebank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
public class LoginController {
    private UserService userService;

    @GetMapping
    //Check to ensure the entered information matches a user in the database
    public ResponseEntity<User> loginCheck(@RequestBody Map<String, String> loginInfo) {
        if(userService.isValidLogin(loginInfo))
            return new ResponseEntity<>(userService.getLoginUser(loginInfo), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
