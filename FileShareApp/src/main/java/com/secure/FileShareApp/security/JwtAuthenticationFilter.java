package com.secure.FileShareApp.security;

import com.secure.FileShareApp.entity.User;
import com.secure.FileShareApp.service.JwtService;
import com.secure.FileShareApp.service.UserService;
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

    private final JwtService jwtService;

    private final UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;
        System.out.println("authHeader" + authHeader);
        System.out.println("request came here"+request.getRemoteAddr());
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("invalid auth header");
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        email = jwtService.extractUsername(jwt);
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = (User) userService.loadUserByUsername(email);
            System.out.println("user found");
            if (jwtService.isTokenValid(jwt,user)){
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities()));
                System.out.println("authenticated");
            }

        }
        filterChain.doFilter(request,response);
    }
}
