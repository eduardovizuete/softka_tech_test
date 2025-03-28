package com.job.micro.security.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JwkSetController {

    private final JWKSet jwkSet;

    public JwkSetController(JWKSet jwkSet) {
        this.jwkSet = jwkSet;
    }

    @GetMapping("/oauth2/jwks")
    public ResponseEntity<Map<String, Object>> getJwkSet() {
        List<Map<String, Object>> keys = jwkSet.getKeys().stream()
                .filter(RSAKey.class::isInstance) // Ensure the JWK is an RSAKey
                .map(jwk -> {
                    RSAKey rsaKey = (RSAKey) jwk; // Cast to RSAKey
                    Map<String, Object> keyDetails = new HashMap<>();
                    keyDetails.put("kty", rsaKey.getKeyType().getValue()); // Ensure you get the value
                    keyDetails.put("e", rsaKey.getPublicExponent().toString());
                    keyDetails.put("n", rsaKey.getModulus().toString());
                    keyDetails.put("kid", rsaKey.getKeyID()); // Add the key ID
                    keyDetails.put("alg", rsaKey.getAlgorithm().getName());
                    keyDetails.put("use", rsaKey.getKeyUse().getValue());
                    return keyDetails;
                })
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("keys", keys);

        return ResponseEntity.ok(response);
    }
    
}
