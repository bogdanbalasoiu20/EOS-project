package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.AesKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

//filtru de autentificare; se executa automat pentru fiecare request inainte de a ajunge la controller
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                JwtConsumer jwtConsumer = new JwtConsumerBuilder()  //creez un validator pt token-urile jwt; specific tot ce trebuie sa contina un token
                        .setRequireExpirationTime()  //token-ul trebuie sa contina data de expirare
                        .setVerificationKey(new AesKey(jwtSecret.getBytes(StandardCharsets.UTF_8)))  //cheia folosita pt verificarea semnaturii
                        .setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.HMAC_SHA256)  //accept doar token-uri semnate cu HS256
                        .build();

                var claims = jwtConsumer.processToClaims(token); //validez tokenul si extrag informatiile
                String email = claims.getSubject(); //daca e verifcarea a trecut, iau emailul din token

                //Tokenul este valid; autentific utilizatorul in Spring Security
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList()));

            } catch (InvalidJwtException | MalformedClaimException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token");
                return;
            }
        }

        //trec la urmatorul filtru de securitate
        filterChain.doFilter(request, response);
    }
}