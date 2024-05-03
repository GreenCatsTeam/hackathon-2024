//package com.greencats.repository.jdbc;
//
//import com.greencats.dto.authorization.AuthUserInfo;
//import com.greencats.exception.UserAlreadyExistException;
//import com.greencats.exception.UserNotFoundException;
//import com.greencats.repository.AuthRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.simple.JdbcClient;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@RequiredArgsConstructor
//public class JdbcAuthRepository implements AuthRepository {
//
//    private final JdbcClient client;
//
//    private static final String EMAIL_FIELD = "email";
//
//    private static final String PASSWORD_FIELD = "password";
//
//    @Override
//    public Long performLogin(AuthUserInfo authUserInfo) {
//        return client.sql("SELECT user_id FROM users WHERE email = :email AND password = :password")
//            .param(EMAIL_FIELD, authUserInfo.email())
//            .param(PASSWORD_FIELD, authUserInfo.password())
//            .query(Long.class)
//            .optional().orElseThrow(UserNotFoundException::new);
//    }
//
//    @Override
//    public Long performRegistration(AuthUserInfo authUserInfo) throws UserAlreadyExistException {
//        // Проверяем, существует ли пользователь с таким email
//        boolean userExists = client.sql("SELECT COUNT(*) > 0 FROM users WHERE email = :email")
//            .param(EMAIL_FIELD, authUserInfo.email())
//            .query(Boolean.class)
//            .optional().orElse(false);
//
//        if (userExists) {
//            throw new UserAlreadyExistException();
//        }
//
//        // Если пользователь не существует, выполняем вставку
//        return client.sql("INSERT INTO users (email, password) VALUES(:email, :password) RETURNING user_id")
//            .param(EMAIL_FIELD, authUserInfo.email())
//            .param(PASSWORD_FIELD, authUserInfo.password())
//            .query(Long.class)
//            .optional().orElseThrow(() -> new RuntimeException("Failed to create user for unknown reasons"));
//    }
//
//    @Override
//    public String getEncodedPasswordByEmail(String email) {
//        return client.sql("SELECT password FROM users WHERE email = :email")
//            .param(EMAIL_FIELD, email)
//            .query(String.class)
//            .optional()
//            .orElseThrow(UserNotFoundException::new);
//    }
//
//    @Override
//    public Long getUserIdByEmail(String email) {
//        return client.sql("SELECT user_id FROM users WHERE email = :email")
//            .param(EMAIL_FIELD, email)
//            .query(Long.class)
//            .optional()
//            .orElseThrow(UserNotFoundException::new);
//    }
//
//}
