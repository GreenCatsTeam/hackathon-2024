package com.greencats.service;

import com.greencats.hackathon.model.UserInfoForAdminPanel;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface AdminService {

    ResponseEntity<List<UserInfoForAdminPanel>> adminUsersGet(Integer limit, Integer offset);
    ResponseEntity<Void> adminBanUser(Long id);
}
