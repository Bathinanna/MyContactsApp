package com.seveneleven.UserRegistration;

import java.util.Optional;

public class AuthService {
    private Authentication authentication;

    public AuthService(Authentication authentication) {
        this.authentication = authentication;
    }

    public Optional<User> login(String email, String password) {
        return authentication.authenticate(email, password);
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}