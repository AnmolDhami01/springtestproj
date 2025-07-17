package com.newSpring.testApp.config;

import com.newSpring.testApp.modal.JwtModal;
import com.newSpring.testApp.modal.UserModal;
import com.newSpring.testApp.modal.repo.JwtRepo;
import com.newSpring.testApp.modal.repo.UsersRepo;

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
