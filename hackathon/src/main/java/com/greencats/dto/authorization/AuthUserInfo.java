package com.greencats.dto.authorization;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

public record AuthUserInfo(@Valid @Max(30) String firstName, @Max(30) String lastName, @Email String email,
                           @Size(min = 8, max = 100) String password, String role, String organization,
                           String cityName, String districtName) {
}
