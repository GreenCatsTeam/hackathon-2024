package com.greencats.configuration;

import com.greencats.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(customizer -> {
                customizer.requestMatchers(HttpMethod.POST, "/users").permitAll();
                customizer.anyRequest().authenticated();
            })
            .oauth2Login(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
            .userDetailsService(userDetailsService)
            .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }
}
