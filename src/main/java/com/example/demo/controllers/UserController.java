package com.example.demo.controllers;

import com.example.demo.exceptions.DataValidFormatException;
import com.example.demo.payloads.UserDto;
import com.example.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users/")
@Tag(name = "User Controller", description = "APIs for managing users")
public class UserController {

    @Autowired
    private UserService userService;

//    POST - Creating new User
    @PostMapping("/")
    @Operation(summary = "Create a new User !!", description = "This is user api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success ! OK"),
            @ApiResponse(responseCode = "401", description = "Not Authorized !!"),
            @ApiResponse(responseCode = "201", description = "New User Created !")
    })
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("Errors found");
            List<DataValidFormatException> invalidFieldList = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(error -> {
                invalidFieldList.add(new DataValidFormatException(error.getField(), error.getDefaultMessage()));
            });
            return new ResponseEntity<>(invalidFieldList, HttpStatus.BAD_REQUEST);
        }
        UserDto createdUser = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

//    PUT - Update user
    @PutMapping("/{userId}")
    @Operation(summary = "Update a existing User !!", description = "This is user api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success ! OK"),
            @ApiResponse(responseCode = "401", description = "Not Authorized !!"),
    })
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId")Integer userId, @RequestBody UserDto userDto)
    {
        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

//    DELETE - delete User
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    @Operation(summary = "DELETE a user !!", description = "This is admin api")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId){
        try {
            this.userService.deleteUser(userId);
            return new ResponseEntity<>(Map.of("message", "User Deleted Successfully.."), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.ok("Unable to process the request");
        }
    }

//    GET - get user
    @GetMapping("/")
    @Operation(summary = "Get a user by using UserId", description = "This is a user api")
    public ResponseEntity<List<UserDto>> getUser(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> singleUser(@PathVariable("userId") Integer userId){
        try {
            return ResponseEntity.ok(this.userService.getUserById(userId));
        }
        catch (Exception e){
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
    }

}
