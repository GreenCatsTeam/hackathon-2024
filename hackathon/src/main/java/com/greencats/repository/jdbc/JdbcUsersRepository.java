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
        int affectedRows =
            client.sql("UPDATE users SET " +
                    "first_name =:first_name, last_name =:last_name, email =:email, password = :password, role =: role, organization := organization " +
                    "WHERE user_id = :id")
                .param("first_name", userEditInfo.first_name())
                .param("last_name", userEditInfo.last_name())
                .param(EMAIL_FIELD, userEditInfo.email())
                .param(PASSWORD_FIELD, userEditInfo.password())
                .param("role", userEditInfo.role())
                .param("organization", userEditInfo.organization())
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
            "SELECT Card.card_id, Card.complexity, Card.longitude, Card.latitude, m.max_status, Card.city_id, Card.district_id " +
                "FROM Card INNER JOIN maxstatus m ON Card.card_id = m.card_id " +
                "INNER JOIN Cleaning ON Card.card_id = Cleaning.card_id" +
                "WHERE Card.is_deleted != true AND Cleaning.user_id =:id" +
                "LIMIT :limit OFFSET :offset"
        )
        .param("id", id)
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

//@Override
//public List<ShortCardInfo> getListCards(Integer limit, Integer offset) {
//    return client.sql(
//            "SELECT Card.card_id, Card.complexity, Card.longitude, Card.latitude, m.max_status, Card.city_id, Card.district_id " +
//                "FROM Card INNER JOIN maxstatus m ON Card.card_id = m.card_id " +
//                "WHERE Card.is_deleted != true " +
//                "LIMIT :limit OFFSET :offset"
//        )
//        .param("limit", limit)
//        .param("offset", offset)
//        .query(ShortCardInfo.class).list();
//}
