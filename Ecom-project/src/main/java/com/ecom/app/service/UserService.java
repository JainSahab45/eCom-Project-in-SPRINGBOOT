package com.ecom.app.service;

import com.ecom.app.entity.User;
import com.ecom.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private List<User> userList = new ArrayList<>();
    private Long nextId = 1L;
    
    public List<User> fetchAllUsers(){
        return userList;
    }

    public void addUser(User user){
        user.setId(nextId++);
        userList.add(user);
//        return "User added successfully";
    }

    public Optional<User> fetchUser(Long id){
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public boolean updateUser(Long id , User updateduser){
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updateduser.getFirstName());
                    existingUser.setLastName(updateduser.getLastName());
                    return true;
                }).orElse(false);
    }

}
