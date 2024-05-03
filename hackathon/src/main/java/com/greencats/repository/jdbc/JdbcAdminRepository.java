package com.greencats.repository.jdbc;

import com.greencats.dto.admin.UserInfo;
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
        return List.of();
    }
}
