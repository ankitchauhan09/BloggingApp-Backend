package com.example.demo.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String name;
    private String password;
}
