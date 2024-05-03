package com.greencats.service.jdbc;

import com.greencats.dto.admin.UserInfo;
import com.greencats.hackathon.model.UserInfoForAdminPanel;
import com.greencats.repository.AdminRepository;
import com.greencats.repository.UsersRepository;
import com.greencats.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

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
            userForAnswer.setCityId(user.cityId());
            userForAnswer.setDistrictId(user.districtId());
            userForAnswer.setIsBanned(user.isBanned());
            usersInfoForAdminPanel.add(userForAnswer);
        }
        return new ResponseEntity<>(usersInfoForAdminPanel, HttpStatus.OK);
    }
}
