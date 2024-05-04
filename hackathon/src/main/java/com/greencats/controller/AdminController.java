package com.greencats.controller;

import com.greencats.hackathon.api.AdminApi;
import com.greencats.hackathon.model.UserInfoForAdminPanel;
import com.greencats.service.AdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final AdminService adminService;

    @Override
    public ResponseEntity<List<UserInfoForAdminPanel>> adminUsersGet(Integer limit, Integer offset) {
        return adminService.adminUsersGet(limit, offset);
    }

    @Override
    public ResponseEntity<Void> banUserById(Long id) {
        return adminService.adminBanUser(id);
    }

    @Override
    public ResponseEntity<Void> changeUserRights(Long id, Long role) {
        return AdminApi.super.changeUserRights(id, role);
    }
}
