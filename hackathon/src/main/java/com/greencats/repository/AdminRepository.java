package com.greencats.repository;

import com.greencats.dto.admin.UserInfo;
import com.greencats.hackathon.model.UserInfoForAdminPanel;
import java.util.List;

public interface AdminRepository {

    List<UserInfo> adminUsersGet(Integer limit, Integer offset);
}
