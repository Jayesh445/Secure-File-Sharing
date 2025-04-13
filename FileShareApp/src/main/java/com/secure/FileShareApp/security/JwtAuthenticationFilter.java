package com.secure.FileShareApp.security;

import com.secure.FileShareApp.entity.User;
import com.secure.FileShareApp.service.TokenBlacklistService;
import com.secure.FileShareApp.service.UserService;
import com.secure.FileShareApp.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request,
                                    @SuppressWarnings("null") HttpServletResponse response,
                                    @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        System.out.println("[Request] " + method + " " + path);
        System.out.println("[From IP] " + request.getRemoteAddr());
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;
        System.out.println("authHeader" + authHeader);
        System.out.println("request came here" + request.getRemoteAddr());
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        if (tokenBlacklistService.isBlacklisted(jwt)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
            return;
        }
        email = jwtUtils.extractUsername(jwt);
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = (User) userService.loadUserByUsername(email);
            System.out.println("user found");
            if (jwtUtils.isTokenValid(jwt, user)) {
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
                System.out.println("authenticated");
            }

        }
        filterChain.doFilter(request, response);
    }
}
