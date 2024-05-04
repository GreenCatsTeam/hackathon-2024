package com.greencats.controller;

import com.greencats.hackathon.api.AdminApi;
import com.greencats.hackathon.model.UserInfoForAdminPanel;
import com.greencats.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final AdminService adminService;

    @Override
    public ResponseEntity<List<UserInfoForAdminPanel>> adminUsersGet(Integer limit, Integer offset) {
        return adminService.adminUsersGet(limit, offset);
    }
}
