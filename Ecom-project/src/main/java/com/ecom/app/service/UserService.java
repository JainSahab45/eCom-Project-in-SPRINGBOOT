package com.ecom.app.service;

import com.ecom.app.dto.AddressDto;
import com.ecom.app.dto.UserRequest;
import com.ecom.app.dto.UserResponse;
import com.ecom.app.entity.Address;
import com.ecom.app.entity.User;
import com.ecom.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private List<User> userList = new ArrayList<>();
    //    private Long nextId = 1L;

    public List<UserResponse> fetchAllUsers(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }
    private UserResponse mapToUserResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setRole(user.getRole());

        if(user.getAddress() != null){
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setZip(user.getAddress().getZip());
            userResponse.setAddress(addressDto);
        }
        return userResponse;
    }

    public void addUser(UserRequest userRequest){
        User user = new User();
        updateUserFromRequest(user , userRequest);
        userRepository.save(user);
//        return "User added successfully";
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        if(userRequest.getAddress() != null){
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZip(userRequest.getAddress().getZip());
            user.setAddress(address);
        }
    }

    public Optional<UserResponse> fetchUser(Long id){
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(Long id , UserRequest updateduserRequest){
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updateduserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

}
