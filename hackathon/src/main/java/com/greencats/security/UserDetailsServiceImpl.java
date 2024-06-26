package com.greencats.security;

import com.greencats.dto.security.UserCredentials;
import com.greencats.exception.UserBannedException;
import com.greencats.repository.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserCredentials authUserInfo = usersRepository.findByEmail(username);

        if (authUserInfo.isBanned()) {
            throw new UserBannedException();
        }

        List<String> roles = new ArrayList<>();
        roles.add("USER"); // базовая роль
        if (authUserInfo.isAdmin()) { // предполагаем, что есть метод isAdmin() в вашей модели данных
            roles.add("ADMIN");
        }

        return User
            .withUsername(authUserInfo.email())
            .password(authUserInfo.password())
            .roles(roles.toArray(new String[0]))
            .build();
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // SHOW_ACCOUNT, WITHDRAW_MONEY, SEND_MONEY
//        // ROLE_ADMIN, ROLE_USER - это роли
//        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
//    }

}
