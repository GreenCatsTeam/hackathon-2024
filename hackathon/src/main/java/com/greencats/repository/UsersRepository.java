package com.greencats.repository;

import com.greencats.dto.card.ShortCardInfo;
import com.greencats.dto.security.UserCredentials;
import com.greencats.dto.user.UserEditInfo;
import java.util.List;

public interface UsersRepository {

    Long usersIdDelete(Long id);

    Long usersIdPut(Long id, UserEditInfo userEditInfo);

    UserCredentials findByEmail(String username);

    List<ShortCardInfo> getUserCardsList(Integer limit, Integer offset, Long id);
    Boolean isBanned(Long id);
}
