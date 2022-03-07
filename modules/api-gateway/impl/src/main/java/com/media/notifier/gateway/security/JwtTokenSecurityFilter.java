package com.media.notifier.gateway.security;

import com.media.notifier.auth.api.exception.AuthenticationException;
import com.media.notifier.common.security.UserDetails;
import com.media.notifier.gateway.domain.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static java.util.Objects.nonNull;

@Service("jwtTokenSecurityFilter")
@RequiredArgsConstructor
public class JwtTokenSecurityFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        try {
            if (nonNull(authorizationHeader)) {
                var jwtToken = authorizationHeader.replace("Bearer ", "");
                var userDetails = jwtTokenService.validateToken(jwtToken);
                var authentication = convertToAuthentication(userDetails);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();

            response.setStatus(403);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            SecurityContextHolder.clearContext();

            throw e;
        }
    }

    private UsernamePasswordAuthenticationToken convertToAuthentication(UserDetails userDetails) {
        SimpleGrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + userDetails.getRole().getValue());

        return new UsernamePasswordAuthenticationToken(userDetails, "", Set.of(roleAuthority));
    }
}
