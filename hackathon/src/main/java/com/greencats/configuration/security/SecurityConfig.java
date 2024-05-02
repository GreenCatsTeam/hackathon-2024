package com.greencats.configuration.security;

import com.greencats.configuration.security.filters.JWTFilter;
import com.greencats.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
                auth -> auth
                    .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/registration").permitAll()
//                auth.requestMatchers(HttpMethod.POST, "/users").permitAll();
                    .anyRequest().authenticated())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//            .oauth2Login(Customizer.withDefaults())
//            .formLogin(Customizer.withDefaults())
            .userDetailsService(userDetailsService)
            .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//            .httpBasic(Customizer.withDefaults());

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
