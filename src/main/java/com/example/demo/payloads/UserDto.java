package com.example.demo.payloads;

import com.example.demo.entities.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private int id;
    @NotEmpty(message = "Name cannot be empty")
    @Schema(name = "user_name", accessMode = Schema.AccessMode.READ_ONLY, description = "username of the new user")
    private String name;
    @Email(message = "Please enter valid email")
    private String email;
    @NotEmpty(message = "password cannot be empty")
    private String password;
    @NotNull
    private String about;
    private Set<Role> roles = new HashSet<>();
}
