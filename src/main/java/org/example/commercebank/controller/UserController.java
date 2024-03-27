package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.User;
import org.example.commercebank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;


    //Add User API
    //PostMapping will take the HTTP request and map it to this method
    //Request Body will extract a JSON into a User object directly
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody User user) {
        userService.deleteUser(user.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
