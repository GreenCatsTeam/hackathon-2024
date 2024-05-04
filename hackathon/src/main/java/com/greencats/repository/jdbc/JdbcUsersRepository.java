package com.greencats.repository.jdbc;

import com.greencats.dto.authorization.AuthUserInfo;
import com.greencats.dto.card.ShortCardInfo;
import com.greencats.dto.security.UserCredentials;
import com.greencats.dto.user.UserEditInfo;
import com.greencats.exception.UserNotFoundException;
import com.greencats.repository.UsersRepository;
import java.util.List;
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
        int affectedRows = client.sql("UPDATE users SET is_deleted = TRUE WHERE user_id = :id")
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

    @Override
    public List<ShortCardInfo> getUserCardsList(Integer limit, Integer offset, Long id) {
        return client.sql( //TODO: change sql query
                "SELECT c.card_id, c.complexity, c.longitude, c.latitude, c.city_id, c.district_id, cl.status_id " +
                    "FROM card c " +
                    "LEFT JOIN ( " +
                    "    SELECT Cleaning.card_id, cleaning.status_id " +
                    "    FROM Cleaning " +
                    "    INNER JOIN ( " +
                    "        SELECT card_id, MAX(time) AS latest_time " +
                    "        FROM Cleaning " +
                    "        GROUP BY card_id " +
                    "    ) AS latest_cleaning ON cleaning.card_id = latest_cleaning.card_id AND cleaning.time = latest_cleaning.latest_time " +
                    ") AS cl ON c.card_id = cl.card_id " +
                    "ORDER BY c.card_id DESC " +
                    "LIMIT :limit OFFSET :offset"
            )
            .param("limit", limit)
            .param("offset", offset)
            .query(ShortCardInfo.class).list();
    }

    @Override
    public Boolean isBanned(Long id) {
        Object result = client.sql("SELECT is_banned FROM Users WHERE user_id = :id")
            .param("id", id)
            .query()
            .rowSet().first();

        return result != null && (Boolean) result;
    }
}
