package com.media.notifier.gateway.domain;

import com.example.util.time.Time;
import com.media.notifier.auth.api.exception.AuthenticationException;
import com.media.notifier.common.security.Role;
import com.media.notifier.common.security.UserDetails;
import com.media.notifier.common.utils.OldTimeAdaptor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {
    @Value("${jwt.secret}")
    private final String secretKey;
    @Value("${jwt.expiration}")
    private final long tokenValidityInSeconds;

    public UserDetails validateToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            var login = claimsJws.getBody().getSubject();
            var roleValue = claimsJws.getBody().get("role", String.class);
            var role = Role.fromString(roleValue);
            return UserDetails.builder()
                    .login(login)
                    .role(role)
                    .build();
        } catch (Exception e) {
            throw new AuthenticationException("Invalid Jwt Token");
        }
    }

    public String createToken(String login, Role role) {
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("role", role.getValue());
        LocalDateTime now = Time.currentDateTime();
        LocalDateTime validity = now.plusSeconds(tokenValidityInSeconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(OldTimeAdaptor.toDate(now))
                .setExpiration(OldTimeAdaptor.toDate(validity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
