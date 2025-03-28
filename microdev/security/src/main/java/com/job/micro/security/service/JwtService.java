package com.job.micro.security.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class JwtService {

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.expiration}") // Expiration time in seconds
    private Long expirationTime;

    private final RSAPrivateKey privateKey;

    @Getter
    private final RSAPublicKey publicKey;

    public JwtService(KeyPair keyPair) {
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

    public String generateToken(String username, Map<String, Object> claims) {
        if (privateKey == null) {
            log.error("Private key is null");
            throw new RuntimeException("Private key is not initialized");
        }

        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer(issuer)
                    .expirationTime(new Date(System.currentTimeMillis() + expirationTime * 1000)) // Use expirationTime from properties
                    .claim("roles", claims.get("roles")) // Store roles if needed
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader.Builder(JWSAlgorithm.RS256)
                            .build(),
                    claimsSet
            );

            RSASSASigner signer = new RSASSASigner(privateKey);
            signedJWT.sign(signer); // Sign the token

            String token = signedJWT.serialize();
            log.info("JWT created for user {}: {}", username, token);
            return token;
        } catch (Exception e) {
            log.error("Error generating JWT token", e);
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    /**
     * Extracts the username from a JWT Token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, JWTClaimsSet::getSubject);
    }

    /**
     * Extracts any claim from a JWT Token.
     */
    public <T> T extractClaim(String token, ClaimsResolver<T> resolver) {
        try {
            JWTClaimsSet claims = parseToken(token);
            return resolver.resolve(claims);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    /**
     * Validates if a token is valid and not expired.
     */
    public boolean validateToken(String token, String username) {
        String name = extractUsername(token);
        boolean isValidToken = name.equals(username) && !isTokenExpired(token);
        log.info("JWT validation for token: {} is valid: {}", token, isValidToken);
        return isValidToken;
    }

    /**
     * Checks if the token has expired.
     */
    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, JWTClaimsSet::getExpirationTime);
        return expiration.before(new Date());
    }

    /**
     * Parses and verifies a JWT token.
     */
    private JWTClaimsSet parseToken(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        return signedJWT.getJWTClaimsSet();
    }

    /**
     * Functional interface for extracting claims.
     */
    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(JWTClaimsSet claims);
    }

}
