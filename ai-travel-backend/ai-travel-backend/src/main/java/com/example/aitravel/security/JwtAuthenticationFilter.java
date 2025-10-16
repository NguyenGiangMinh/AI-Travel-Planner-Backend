package com.example.aitravel.security;

import com.example.aitravel.entity.User;
import com.example.aitravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        try {
            if (!jwtUtil.validateToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            String email = jwtUtil.extractEmail(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // load user from DB (optional: you can set roles here)
                User user = userService.findByEmail(email).orElse(null);
                if (user != null) {
                    // if you have roles, map to GrantedAuthority list
                    var authorities = List.of(new SimpleGrantedAuthority("USER"));
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Authenticated user: " + user.getEmail());
                }
            }
        } catch (Exception ex) {
            // nếu token invalid -> không set authentication, filter chain tiếp tục và sẽ bị 401 tại entry point
            // bạn có thể log nếu cần
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Bỏ qua đường dẫn public
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/");
    }
}