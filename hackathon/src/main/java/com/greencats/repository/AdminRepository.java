package com.greencats.repository;

import com.greencats.dto.admin.UserInfo;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface AdminRepository {

    List<UserInfo> adminUsersGet(Integer limit, Integer offset);

    void adminBanUser(Long id);

    void changeUserRights(Long id, String role);
}
