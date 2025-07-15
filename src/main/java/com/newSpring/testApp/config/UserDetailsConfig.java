package com.newSpring.testApp.config;

import com.newSpring.testApp.modal.UserModal;
import com.newSpring.testApp.modal.repo.UsersRepo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;

@Configuration
public class UserDetailsConfig {

    private final UsersRepo usersRepo;

    public UserDetailsConfig(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserModal user = usersRepo.findByName(username);
                if (user == null) {
                    return null;
                }

                // Map your UserModal to Spring Security's UserDetails
                return User.builder()
                        .username(user.getName())
                        .password(user.getPassword()) // Use encoder in real app!
                        .roles(user.getRole()) // You can pull roles from user if stored
                        .build();
            }
        };
    }
}
