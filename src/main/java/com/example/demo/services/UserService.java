package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.payloads.UserDto;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);

    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUserById(Integer userId);
    java.util.List<UserDto> getAllUsers();
    void deleteUser(Integer userId);

}
