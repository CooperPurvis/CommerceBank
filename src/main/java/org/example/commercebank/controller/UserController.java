package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.User;
import org.example.commercebank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    //Get all users in the database
    @RequestMapping("/all")
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    //Check to ensure the entered information matches a user in the database
    @RequestMapping("/login")
    @GetMapping
    public ResponseEntity<Map<String, String>> loginCheck(@RequestBody Map<String, String> loginInfo) {
        if(userService.isValidLogin(loginInfo))
            return new ResponseEntity<>(userService.getLoginInfo(loginInfo), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //Create a user entity and add it to the database
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        User savedUser = userService.createUser(newUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    //Update user information for a userId in the database
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //Delete an existing user in the database
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody Map<String, String> userInfo) {
        if(!userService.exists(userInfo.get("userId")))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userService.deleteUser(userInfo.get("userId"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
