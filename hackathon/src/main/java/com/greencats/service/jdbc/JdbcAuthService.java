package com.greencats.service.jdbc;

import com.greencats.dto.authorization.AuthUserInfo;
import com.greencats.exception.UserBadCredentialException;
import com.greencats.hackathon.model.JWTToken;
import com.greencats.repository.AuthRepository;
import com.greencats.service.AuthService;
import com.greencats.util.JWTUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcAuthService implements AuthService {

    private final AuthRepository authRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    @Override
    @Transactional
    public ResponseEntity<JWTToken> performLogin(String email, String password) {
        String encodedPasswordFromDB = authRepository.getEncodedPasswordByEmail(email);

        if (passwordEncoder.matches(password, encodedPasswordFromDB)) {
            Long userId = authRepository.getUserIdByEmail(email);
            String token = jwtUtil.generateToken(userId, email, encodedPasswordFromDB);
            JWTToken jwtToken = new JWTToken();
            jwtToken.setJwtToken(token);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<JWTToken> performRegistration(
        String firstName,
        String lastName,
        String email,
        String password,
        String role,
        String organization,
        String cityName,
        String districtName
    ) {
        String encodedPassword = passwordEncoder.encode(password);
        AuthUserInfo authUserInfo = new AuthUserInfo(firstName,
            lastName,
            email,
            encodedPassword,
            role,
            organization,
            cityName,
            districtName
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<AuthUserInfo>> violations = validator.validate(authUserInfo);

        JWTToken jwtToken;
        if (violations.isEmpty()) {
            Long userId = authRepository.performRegistration(authUserInfo);
            String token = jwtUtil.generateToken(userId, email, encodedPassword);
            jwtToken = new JWTToken();
            jwtToken.setJwtToken(token);
        } else {
            throw new UserBadCredentialException();
        }

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
}
