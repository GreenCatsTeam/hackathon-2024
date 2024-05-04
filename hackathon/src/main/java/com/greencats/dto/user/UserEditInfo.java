package com.greencats.dto.user;

public record UserEditInfo(
    String first_name,
    String last_name,
    String email,
    String password,
    String role,
    String organization
) {
}

//  "first_name":"string",
//  "last_name":"string",
//  "email":"string",
//  "password":"string",
//    "role":"string",
//    "organization":"string"
