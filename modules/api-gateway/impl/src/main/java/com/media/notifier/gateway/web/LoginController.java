package com.media.notifier.gateway.web;

import com.media.notifier.auth.api.AuthenticationProvider;
import com.media.notifier.gateway.domain.JwtTokenService;
import com.media.notifier.gateway.web.dto.LoginRequest;
import com.media.notifier.gateway.web.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class LoginController {
    private final JwtTokenService jwtTokenService;
    private final AuthenticationProvider authenticationProvider;

    @PostMapping("/login")
    public LoginResponse authenticate(@RequestBody LoginRequest loginRequest) {
        log.info("Starting login for user: " + loginRequest.getLogin());
        var authenticate = authenticationProvider.authenticate(loginRequest.getLogin(), loginRequest.getPassword());

        var jwtToken = jwtTokenService.createToken(authenticate.getLogin(), authenticate.getRole());
        log.info("Successfully logged in for user: " + loginRequest.getLogin());
        return LoginResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }
}
