package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.User;
import org.example.commercebank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    //Get all users in the database
    @GetMapping
    @CrossOrigin
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    //Create a user entity and add it to the database
    @CrossOrigin
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if(userService.exists(user))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        if(userService.infoMissing(user))
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    //Update user information for a userId in the database
    @PutMapping
    @CrossOrigin
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if(user.getUserUid() == null)
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        if(!userService.exists(user))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        if(userService.infoMissing(user))
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    //Delete an existing user in the database
    @DeleteMapping
    @CrossOrigin
    public ResponseEntity<Void> deleteUser(@RequestBody User user) {
        if(user.getUserUid() == null)
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        if(!userService.exists(user))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
