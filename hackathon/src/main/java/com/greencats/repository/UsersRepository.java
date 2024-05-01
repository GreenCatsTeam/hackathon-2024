package com.greencats.repository;

import java.util.Optional;
import com.greencats.dto.user.UserCreateInfo;
import com.greencats.dto.user.UserEditInfo;
import com.greencats.dto.user.UserInfo;
import com.greencats.exception.UserAlreadyExistException;

public interface UsersRepository {

    Long usersIdDelete(Long id);

    Long usersIdPut(Long id, UserEditInfo userEditInfo);

    Long usersPost(UserCreateInfo userCreateInfo) throws UserAlreadyExistException;

    Optional<UserInfo> findByEmail(String username);
}
