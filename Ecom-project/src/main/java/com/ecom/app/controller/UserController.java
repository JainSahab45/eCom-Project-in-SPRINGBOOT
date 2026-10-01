package com.ecom.app.controller;

import com.ecom.app.entity.User;
import com.ecom.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
        @Autowired
        private final UserService userService;

        @GetMapping
        public ResponseEntity<List<User>> getAllUsers(){
            return new ResponseEntity<>(userService.fetchAllUsers() , HttpStatus.OK);
        }

        @PostMapping
        public ResponseEntity<String> createUser(@RequestBody User user){
            userService.addUser(user);
            return new ResponseEntity<>("User Created", HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<User> getUserById(@PathVariable Long id){
            return userService.fetchUser(id)
                    .map(ResponseEntity :: ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
            boolean updated = userService.updateUser(id, user);
            if(updated){
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        }
}
