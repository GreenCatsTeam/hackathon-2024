package com.greencats.dto.authorization;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AuthUserInfo(@Valid @Email String email, @Size(min = 8, max = 100) String password) {
}
