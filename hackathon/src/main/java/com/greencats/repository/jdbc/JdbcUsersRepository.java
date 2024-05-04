package com.greencats.repository.jdbc;

import com.greencats.dto.card.ShortCardInfo;
import com.greencats.dto.security.UserCredentials;
import com.greencats.dto.user.UserEditInfo;
import com.greencats.exception.UserNotFoundException;
import com.greencats.repository.UsersRepository;
import java.util.List;
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

    public boolean isExistUserById(Long userId) {
        return client.sql("SELECT EXISTS(SELECT 1 FROM users WHERE user_id = :user_id)")
            .param("user_id", userId)
            .query(Boolean.class)
            .optional()
            .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<ShortCardInfo> getUserCardsList(Integer limit, Integer offset, Long id) {
        return client.sql(
                "SELECT Card.card_id, Card.complexity, Card.longitude, Card.latitude, m.max_status, City.city_name, District.district_name " +
                    "FROM Card " +
                    "INNER JOIN maxstatus m ON Card.card_id = m.card_id " +
                    "INNER JOIN city ON Card.city_id = city.city_id " +
                    "INNER JOIN district ON Card.district_id = district.district_id " +
                    "INNER JOIN cleaning ON cleaning.card_id = Card.card_id " +  // Assuming cleaning relates to Card by card_id
                    "WHERE Card.is_deleted != true AND cleaning.cleaning_id = :user_id " +  // Move user_id filtering to WHERE clause
                    "LIMIT :limit OFFSET :offset"
            )
            .param("user_id", id)
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
