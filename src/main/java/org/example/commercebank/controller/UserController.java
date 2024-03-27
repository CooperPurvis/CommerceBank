package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.User;
import org.example.commercebank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

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

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser(@RequestParam JSONObject body) {
        User deletedUser = userService.deleteUser(body.getString("userId"));
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }
}
