package com.levarvizu.fullstacklab.security;

import com.levarvizu.fullstacklab.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Component
public class JwtAuthenticationFilter
                extends OncePerRequestFilter {

        private final JwtService jwtService;

        public JwtAuthenticationFilter(
                        JwtService jwtService) {
                this.jwtService = jwtService;
        }

        @Override
        protected void doFilterInternal(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain)
                        throws ServletException,
                        IOException {

                // Permitir que las solicitudes OPTIONS pasen sin validación de JWT
                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        filterChain.doFilter(request, response);
                        return;
                }

                String header = request.getHeader(
                                "Authorization");

                if (header != null
                                && header.startsWith(
                                                "Bearer ")) {

                        String token = header.substring(7);

                        if (jwtService.isValid(token)) {

                                // 2. Verificar si está invalidado en Redis
                                if (jwtService.isInvalidated(token)) {
                                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                        response.getWriter().write("Token invalidado");
                                        return;
                                }

                                String email = jwtService.extractUsername(token);
                                List<String> roles = jwtService.extractRoles(token);

                                List<SimpleGrantedAuthority> authorities = (roles != null ? roles : List.of()).stream()
                                                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                                                .toList();

                                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                                email, null, authorities);

                                SecurityContextHolder.getContext().setAuthentication(auth);
                        } else {

                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                                "Invalid or expired token");
                                return;

                        }
                }

                filterChain.doFilter(
                                request,
                                response);
        }
}
