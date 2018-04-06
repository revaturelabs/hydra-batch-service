package com.revature.hydra.batch.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        } else {
            String userDetails = Jwts.parser()
                .setSigningKey(System.getenv("AUTH0_SECRET"))
                .parseClaimsJws(header.replace("Bearer: ", ""))
                .getBody()
                .getSubject();

            if (userDetails != null) {

                // handle authentication here
                chain.doFilter(request, response);
            }
        }
    }
}
