package me.shreyeschekuru.dsa.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import me.shreyeschekuru.dsa.data.entity.User;
import me.shreyeschekuru.dsa.data.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class AuthenticatedUser {

    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;

    public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
    }

    @Transactional
    public Optional<User> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> userRepository.findByUsername(userDetails.getUsername()));
    }

    public boolean hasRole(String role) {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> userDetails.getAuthorities()
                        .stream()
                        .anyMatch(authority -> authority.getAuthority().equals(role)))
                .orElse(false);
    }

    public void logout() {
        authenticationContext.logout();
    }

}
