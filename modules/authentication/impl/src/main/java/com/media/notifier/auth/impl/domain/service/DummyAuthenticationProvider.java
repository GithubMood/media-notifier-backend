package com.media.notifier.auth.impl.domain.service;

import com.media.notifier.auth.api.AuthenticationProvider;
import com.media.notifier.auth.api.exception.AuthenticationException;
import com.media.notifier.common.security.Role;
import com.media.notifier.common.security.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class DummyAuthenticationProvider implements AuthenticationProvider {
    @Override
    public UserDetails authenticate(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return UserDetails.builder()
                    .id(1L)
                    .email("admin@email.com")
                    .role(Role.ADMIN)
                    .build();
        }

        throw new AuthenticationException("Authentication failed");
    }
}
