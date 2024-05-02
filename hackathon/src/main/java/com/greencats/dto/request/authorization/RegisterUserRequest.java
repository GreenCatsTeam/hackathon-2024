package com.greencats.dto.request.authorization;

public record RegisterUserRequest(
    String email,
    String password
) {
}
