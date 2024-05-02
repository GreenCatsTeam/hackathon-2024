package com.greencats.service.jdbc;

import com.greencats.dto.authorization.AuthUserInfo;
import com.greencats.hackathon.model.JWTToken;
import com.greencats.repository.AuthRepository;
import com.greencats.service.AuthService;
import com.greencats.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcAuthService implements AuthService {

    private final AuthRepository authRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    @Override
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
    public ResponseEntity<JWTToken> performRegistration(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Long userId = authRepository.performRegistration(new AuthUserInfo(email, encodedPassword));

        String token = jwtUtil.generateToken(userId, email, encodedPassword);
        JWTToken jwtToken = new JWTToken();
        jwtToken.setJwtToken(token);
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
}
