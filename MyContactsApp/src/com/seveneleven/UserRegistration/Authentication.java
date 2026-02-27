package com.seveneleven.UserRegistration;



import java.util.Optional;

public interface Authentication {
    Optional<User> authenticate(String email, String password);
}