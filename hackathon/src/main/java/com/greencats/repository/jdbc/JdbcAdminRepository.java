package com.greencats.repository.jdbc;

import com.greencats.dto.admin.UserInfo;
import com.greencats.exception.CardNotFoundException;
import com.greencats.exception.UserNotFoundException;
import com.greencats.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcAdminRepository implements AdminRepository {

    private final JdbcClient jdbcClient;

    @Override
    public List<UserInfo> adminUsersGet(Integer limit, Integer offset) {
        return jdbcClient.sql("SELECT user_id, firstName, last_name, email, role, isBanned" +
                " FROM users " +
                " order by user_id desc " +
                "limit :limit offset :offset")
            .param("limit", limit)
            .param("offset", offset)
            .query(UserInfo.class).list();
    }

    @Override
    public void adminBanUser(Long id) {
        int updatedRows = jdbcClient.sql("UPDATE Users SET is_banned = TRUE WHERE user_id = :user_id")
            .param("user_id", id)
            .update();

        if (updatedRows == 0) {
            throw new UserNotFoundException();
        }
    }

}

//Long userId,
//String firstName,
//String lastName,
//String email,
//String role,
//Integer cityId,
//Integer districtId,
//Boolean isBanned

//user_id      BIGSERIAL PRIMARY KEY,
//first_name   VARCHAR(100) NOT NULL,
//last_name    VARCHAR(100) NOT NULL,
//email        VARCHAR(255) NOT NULL,
//password     VARCHAR(100) NOT NULL,
//role         VARCHAR(20)  NOT NULL,
//organization VARCHAR(255) NULL
