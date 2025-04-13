package com.secure.FileShareApp.utils;

import com.secure.FileShareApp.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        System.out.println("success " + email);

        // generate your JWT here
        User user = new User();
        user.setEmail(email);
        String token = jwtUtils.generateToken(user);

        // redirect to frontend with token
        String redirectUrl = "https://secure-share.jayesh.works/oauth-success?token=" + token;
        response.sendRedirect(redirectUrl);
    }
}

