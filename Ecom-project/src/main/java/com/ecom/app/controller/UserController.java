package com.ecom.app.controller;

import com.ecom.app.dto.UserRequest;
import com.ecom.app.dto.UserResponse;
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
        public ResponseEntity<List<UserResponse>> getAllUsers(){
            return new ResponseEntity<>(userService.fetchAllUsers() , HttpStatus.OK);
        }

        @PostMapping
        public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
            userService.addUser(userRequest);
            return new ResponseEntity<>("User Created", HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
            return userService.fetchUser(id)
                    .map(ResponseEntity :: ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest){
            boolean updated = userService.updateUser(id, userRequest);
            if(updated){
                return ResponseEntity.ok("User Updated");
            }
            return ResponseEntity.notFound().build();
        }
}
