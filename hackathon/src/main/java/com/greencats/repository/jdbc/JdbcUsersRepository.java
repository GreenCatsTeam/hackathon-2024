package com.greencats.repository.jdbc;

import com.greencats.dto.authorization.AuthUserInfo;
import com.greencats.dto.security.UserCredentials;
import com.greencats.dto.user.UserEditInfo;
import com.greencats.exception.UserNotFoundException;
import com.greencats.repository.UsersRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcUsersRepository implements UsersRepository {

    private final JdbcClient client;

    private static final String EMAIL_FIELD = "email";

    private static final String PASSWORD_FIELD = "password";

    @Override
    public Long usersIdDelete(Long id) {
        int affectedRows = client.sql("DELETE FROM users WHERE user_id = :id")
            .param("id", id)
            .update();

        if (affectedRows == 0) {
            throw new UserNotFoundException();
        }

        return id;
    }

    @Override
    public Long usersIdPut(Long id, UserEditInfo userEditInfo) {
        int affectedRows = client.sql("UPDATE users SET email = :email, password = :password WHERE user_id = :id")
            .param(EMAIL_FIELD, userEditInfo.email())
            .param(PASSWORD_FIELD, userEditInfo.password())
            .param("id", id)
            .update();

        if (affectedRows == 0) {
            throw new UserNotFoundException();
        }

        return id;
    }

    @Override
    public UserCredentials findByEmail(String email) {
        return client.sql("SELECT email, password, role FROM users WHERE email = :email")
            .param(EMAIL_FIELD, email)
            .query(UserCredentials.class)
            .optional().orElseThrow(UserNotFoundException::new);
    }

    public boolean isExistUserById(Long userId) {
        return client.sql("SELECT EXISTS(SELECT 1 FROM users WHERE user_id = :user_id)")
            .param("user_id", userId)
            .query(Boolean.class)
            .optional()
            .orElseThrow(UserNotFoundException::new);
    }
}
