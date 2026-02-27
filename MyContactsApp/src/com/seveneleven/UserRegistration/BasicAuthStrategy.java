package com.seveneleven.UserRegistration;

import java.util.Optional;

public class BasicAuthStrategy implements Authentication {
    private UserService userService;

    public BasicAuthStrategy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        return userService.findUserByEmailAndPassword(email, password);
    }
}