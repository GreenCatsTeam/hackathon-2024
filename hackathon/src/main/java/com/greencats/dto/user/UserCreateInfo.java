package com.greencats.dto.user;

public record UserCreateInfo(
    String email,
    String password
) {
}
