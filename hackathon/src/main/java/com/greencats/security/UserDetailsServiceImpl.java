package com.greencats.security;

import com.greencats.dto.user.UserInfo;
import com.greencats.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user =
            usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return User.withDefaultPasswordEncoder() // encrypt?
            .username(user.email())
            .password(user.password())
            .roles("USER")
            .build();
    }
}
