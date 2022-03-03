package com.example.s7.repository;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;

@Repository
public class TokenRepository implements CsrfTokenRepository {

    private static final int EXPIRY_HOUR = 24;
    private final String secret;
    public TokenRepository() {
        this.secret = "springrest";
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest httpServletRequest) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, EXPIRY_HOUR);
        Date now = new Date();
        String token = "";
        try {
            token = Jwts.builder()
                    .setId(id)
                    .setIssuedAt(now)
                    .setNotBefore(now)
                    .setExpiration(calendar.getTime())
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
        } catch (JwtException e) {
            e.printStackTrace();
            //ignore
        }
        return new DefaultCsrfToken("x-csrf-token", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
        if (Objects.nonNull(csrfToken)) {
            if (!response.getHeaderNames().contains(ACCESS_CONTROL_EXPOSE_HEADERS))
                response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, csrfToken.getHeaderName());

            if (response.getHeaderNames().contains(csrfToken.getHeaderName()))
                response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
            else
                response.addHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }

    public void clear(HttpServletResponse response) {
        if (response.getHeaderNames().contains("x-csrf-token"))
            response.setHeader("x-csrf-token", "");
    }
}
