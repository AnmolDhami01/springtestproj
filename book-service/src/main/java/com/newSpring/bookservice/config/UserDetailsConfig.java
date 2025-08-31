package com.newSpring.bookservice.config;

import com.newSpring.bookservice.modal.JwtModal;
import com.newSpring.bookservice.modal.UserModal;
import com.newSpring.bookservice.modal.repo.JwtRepo;
import com.newSpring.bookservice.modal.repo.UsersRepo;
import com.newSpring.bookservice.config.CustomUserDetails;
import com.newSpring.bookservice.config.UserDetailsConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;

@Configuration
public class UserDetailsConfig {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private JwtRepo jwtRepo;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserModal user = usersRepo.findByName(username);
                if (user == null) {
                    return null;
                }

                return new CustomUserDetails(
                        user.getName(),
                        user.getPassword(),
                        user.getRole(),
                        user.getId());
            }

        };
    }

    public boolean matchToken(String token, Long userId) {
        JwtModal jwtModal = jwtRepo.findByUserId(userId);
        if (jwtModal == null) {
            return false;
        }
        if (!token.equals(jwtModal.getJwtToken())) {
            return false;
        }
        return true;
    }
}
