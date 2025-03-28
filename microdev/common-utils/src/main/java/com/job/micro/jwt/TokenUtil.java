package com.job.micro.jwt;

import jakarta.servlet.http.HttpServletRequest;

public class TokenUtil {

    public static String extractBearerToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Extract the token
        }
        return null; // Return null if no token is found
    }

}