package com.media.notifier.auth.impl.domain.service;

import com.media.notifier.auth.api.AuthenticationProvider;
import com.media.notifier.auth.api.exception.AuthenticationException;
import com.media.notifier.auth.impl.domain.integration.db.repository.UserRepository;
import com.media.notifier.common.security.Role;
import com.media.notifier.common.security.UserDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DatabaseAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;

    @Override
    public UserDetails authenticate(String login, String password) {
        var user = userRepository.findUser(login, password)
                .orElseThrow(() -> new AuthenticationException("Auth failed"));

        return UserDetails.builder()
                .id(1L)
                .login(user.getLogin())
                .role(Role.ADMIN)
                .build();
    }
}
