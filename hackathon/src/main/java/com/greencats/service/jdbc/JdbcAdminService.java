package com.greencats.service.jdbc;

import com.greencats.dto.admin.UserInfo;
import com.greencats.exception.RoleNotFoundException;
import com.greencats.hackathon.api.AdminApi;
import com.greencats.hackathon.model.UserInfoForAdminPanel;
import com.greencats.repository.AdminRepository;
import com.greencats.service.AdminService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcAdminService implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    @Transactional
    public ResponseEntity<List<UserInfoForAdminPanel>> adminUsersGet(Integer limit, Integer offset) {
        List<UserInfo> users = adminRepository.adminUsersGet(limit, offset);

        List<UserInfoForAdminPanel> usersInfoForAdminPanel = new ArrayList<>();
        for (UserInfo user : users) {
            UserInfoForAdminPanel userForAnswer = new UserInfoForAdminPanel();
            userForAnswer.setUserId(user.userId());
            userForAnswer.setFirstName(user.firstName());
            userForAnswer.setLastName(user.lastName());
            userForAnswer.setEmail(user.email());
            userForAnswer.setRole(user.role());
            userForAnswer.setIsBanned(user.isBanned());
            usersInfoForAdminPanel.add(userForAnswer);
        }
        return new ResponseEntity<>(usersInfoForAdminPanel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> adminBanUser(Long id) {
        adminRepository.adminBanUser(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> changeUserRights(Long id, String role) {
        if (role.equals("USER") || role.equals("ADMIN")) {
            adminRepository.changeUserRights(id, role);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        throw new RoleNotFoundException();
    }
}
