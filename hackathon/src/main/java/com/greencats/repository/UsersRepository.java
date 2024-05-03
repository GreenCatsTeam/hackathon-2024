package com.greencats.repository;

import com.greencats.dto.authorization.AuthUserInfo;
import com.greencats.dto.user.UserEditInfo;
import java.util.Optional;

public interface UsersRepository {

    Long usersIdDelete(Long id);

    Long usersIdPut(Long id, UserEditInfo userEditInfo);

    Optional<AuthUserInfo> findByEmail(String username);
}
