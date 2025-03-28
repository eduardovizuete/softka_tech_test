package com.job.micro.security.controller;

import com.job.micro.security.dto.LoginRequest;
import com.job.micro.security.dto.TokenResponse;
import com.job.micro.security.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        // Authenticate user and get the Authentication object
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Extract user details from Authentication object
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Extract roles from user details
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // Generate token using extracted username & roles
        String token = jwtService.generateToken(userDetails.getUsername(), Map.of("roles", roles));

        return ResponseEntity.ok(new TokenResponse(token));
    }

}

