package uz.alifservice.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uz.alifservice.util.JwtUtil;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler{

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.createOrUpdateOAuth2User(email, name);
        String accessToken = jwtUtil.generateAccessToken(userDetails.getId());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getId());

        // Redirect to frontend with tokens and user info
        String redirectUrl = String.format("http://localhost:3000/oauth2/callback?email=%s&name=%s&accessToken=%s&refreshToken=%s",
                URLEncoder.encode(email, StandardCharsets.UTF_8),
                URLEncoder.encode(name, StandardCharsets.UTF_8),
                URLEncoder.encode(accessToken, StandardCharsets.UTF_8),
                URLEncoder.encode(refreshToken, StandardCharsets.UTF_8));
        response.sendRedirect(redirectUrl);
    }
}
