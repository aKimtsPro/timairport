package be.technifutur.java.timairport.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String suffix = "Bearer ";
        String token;
        if (
                authHeader != null &&
                authHeader.startsWith(suffix) &&
                jwtProvider.validateToken(token = authHeader.replace(suffix, ""))
        ) {
            Authentication auth = jwtProvider.generateAuth( token );
            SecurityContextHolder.getContext().setAuthentication( auth );
        }

        filterChain.doFilter(request, response);

    }
}
