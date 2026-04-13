package com.example.apigateway.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import com.example.apigateway.config.JwtUntil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUntil jwtUtil;

    public JwtAuthFilter(JwtUntil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Missing or invalid Authorization header\"}");
            return;
        }

        String token = authHeader.substring(7);

        if (jwtUtil.isTokenExpired(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token has expired\"}");
            return;
        }

        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
        mutableRequest.putHeader("X-User-Id", jwtUtil.extractUserId(token));
        mutableRequest.putHeader("X-Username", jwtUtil.extractUsername(token));
        mutableRequest.putHeader("X-Role", jwtUtil.extractRole(token));

        filterChain.doFilter(mutableRequest, response);
    }
}
