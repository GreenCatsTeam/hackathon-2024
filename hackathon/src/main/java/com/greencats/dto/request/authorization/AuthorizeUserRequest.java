package com.greencats.dto.request.authorization;

public record AuthorizeUserRequest (
    String email,
    String password
) {
}
