package com.greencats.service;

import com.greencats.hackathon.api.AdminApi;
import com.greencats.hackathon.model.UserInfoForAdminPanel;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    ResponseEntity<List<UserInfoForAdminPanel>> adminUsersGet(Integer limit, Integer offset);

    ResponseEntity<Void> adminBanUser(Long id);

    ResponseEntity<Void> changeUserRights(Long id, String role);
}
