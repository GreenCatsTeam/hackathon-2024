package com.greencats.dto.authorization;

import jakarta.validation.constraints.Email;

public record AuthUserInfo(String firstName, String lastName, @Email String email,
                           String password, String role, String organization,
                           String cityName, String districtName) {
}
