package com.greencats.repository.jdbc;

import com.greencats.dto.admin.UserInfo;
import com.greencats.exception.UserNotFoundException;
import com.greencats.repository.AdminRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcAdminRepository implements AdminRepository {

    private final JdbcClient jdbcClient;

    @Override
    public List<UserInfo> adminUsersGet(Integer limit, Integer offset) {
        return jdbcClient.sql("SELECT user_id, users.first_name, last_name, email, role, is_banned" +
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

    @Override
    public void changeUserRights(Long id, String role) {
        int updatedRows = jdbcClient.sql("UPDATE Users SET role = :role WHERE user_id = :user_id")
            .param("user_id", id)
            .param("role", role)
            .update();

        if (updatedRows == 0) {
            throw new UserNotFoundException();
        }
    }
}
