package com.levarvizu.fullstacklab.service.impl;

import com.levarvizu.fullstacklab.entity.User;
import com.levarvizu.fullstacklab.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class JwtServiceImpl implements JwtService {

        @Value("${jwt.secret}")
        private String secret;

        @Value("${jwt.expiration}")
        private long jwtExpirationMs;

        private final RedisTemplate<String, String> redisTemplate;

        private static final String BLACKLIST_PREFIX = "blacklist:";

        JwtServiceImpl(RedisTemplate redisTemplate) {
                this.redisTemplate = redisTemplate;
        }

        @Override
        public String generateToken(
                        User user) {

                return Jwts.builder()
                                .subject(user.getEmail())
                                .claim("roles", user.getRoles())
                                .issuedAt(new Date())
                                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                                .signWith(getSigningKey())
                                .compact();
        }

        @Override
        public String extractUsername(
                        String token) {

                Claims claims = Jwts.parser()
                                .verifyWith(getSigningKey())
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();

                return claims.getSubject();
        }

        @Override
        public boolean isValid(
                        String token) {

                try {

                        try {
                                Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
                                return true;
                        } catch (ExpiredJwtException e) {
                                return true; // permitir refresh aunque esté expirado
                        } catch (Exception e) {
                                return false;
                        }

                } catch (Exception ex) {

                        return false;
                }
        }

        public String refreshToken(String oldToken) {
                if (!isValid(oldToken)) {
                        throw new RuntimeException("Invalid token");
                }
                String username = extractUsername(oldToken);
                List<String> roles = extractRoles(oldToken);

                SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                return Jwts.builder()
                                .subject(username)
                                .claim("roles", roles)
                                .issuedAt(new Date())
                                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                                .signWith(key)
                                .compact();
        }

        public void invalidateToken(String token, String reason) {
                Claims claims = extractAllClaims(token);
                Date expiration = claims.getExpiration();

                // Calcular TTL en segundos, asegurando que no sea negativo
                long ttl = Math.max((expiration.getTime() - System.currentTimeMillis()) / 1000, 0);

                // Guardar token en Redis con TTL igual al tiempo restante
                redisTemplate.opsForHash().put("blacklist:" + token, "status", "invalidated");
                redisTemplate.opsForHash().put("blacklist:" + token, "reason", reason);
                redisTemplate.expire("blacklist:" + token, Duration.ofSeconds(ttl));
        }

        @Override
        public List<String> extractRoles(String token) {
                Claims claims = extractAllClaims(token);
                Object rolesObject = claims.get("roles");

                if (rolesObject instanceof List<?>) {
                        return ((List<?>) rolesObject).stream()
                                        .map(Object::toString)
                                        .toList();
                }

                // Si no hay roles o el claim está vacío
                return List.of();
        }

        private Claims extractAllClaims(String token) {
                return Jwts.parser()
                                .verifyWith(getSigningKey())
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();
        }

        private SecretKey getSigningKey() {
                return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }

        public boolean isInvalidated(String token) {
                return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
        }
}
