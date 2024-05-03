package com.greencats.dto.admin;

public record UserInfo(
    Long userId,
    String firstName,
    String lastName,
    String email,
    String role,
    Boolean isBanned
) {
}
