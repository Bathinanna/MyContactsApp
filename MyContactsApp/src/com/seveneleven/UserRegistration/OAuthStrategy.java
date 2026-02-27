package com.seveneleven.UserRegistration;

import java.util.Optional;

// Dummy OAuth for UC2 demonstration
public class OAuthStrategy implements Authentication {

    @Override
    public Optional<User> authenticate(String email, String password) {
        // In real app this checks Google/GitHub token
        // Here simple demo rule:
        if (email != null && email.endsWith("@oauth.com") && "oauth123".equals(password)) {
            User oauthUser = new User("OAuth User", email, "NA", "NA", "NA", "FREE");
            return Optional.of(oauthUser);
        }
        return Optional.empty();
    }
}