package io.submanager.config;

import io.submanager.service.CustomUserDetailsService;
import io.submanager.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        doFilterInternal(request);
        filterChain.doFilter(request, response);
    }

    private void doFilterInternal(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (Objects.isNull(authHeader)) {
            logger.warn("Auth token is not present.");
            return;
        }

        if (!authHeader.startsWith("Bearer ")) {
            logger.warn("Auth token does not start with Bearer string.");
            return;
        }

        String jwtToken = authHeader.substring(7);
        String email = jwtService.getEmailFromToken(jwtToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (!jwtService.validateToken(jwtToken, userDetails)) {
            logger.warn("JWT token is invalid.");
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
