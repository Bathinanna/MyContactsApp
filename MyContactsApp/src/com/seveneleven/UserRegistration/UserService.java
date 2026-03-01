package com.seveneleven.UserRegistration;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    private List<User> users = new ArrayList<>();

    // UC-01 Registration
    public User registerUser(String name, String email, String password, String phone, String address, String userType)
            throws ValidationException {

        validateName(name);
        validateEmail(email);
        validatePassword(password);
        validatePhone(phone);
        validateAddress(address);
        validateUserType(userType);
        checkDuplicateEmail(email);

        String hashedPassword = hashPassword(password);

        User user = new User(name, email, hashedPassword, phone, address, userType.toUpperCase());
        users.add(user);
        return user;
    }

    // UC-02 Basic Authentication support
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        if (email == null || email.trim().isEmpty()) return Optional.empty();
        if (password == null || password.trim().isEmpty()) return Optional.empty();

        String hashedPassword = hashPassword(password);

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)
                    && user.getPasswordHash().equals(hashedPassword)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    // UC-03 Update Profile
    public void updateProfile(User user, String newName, String newPhone, String newAddress) throws ValidationException {
        if (user == null) throw new ValidationException("User is required.");
        validateName(newName);
        validatePhone(newPhone);
        validateAddress(newAddress);

        user.setName(newName);
        user.setPhone(newPhone);
        user.setAddress(newAddress);
    }

    // UC-03 Change Password
    public void changePassword(User user, String oldPassword, String newPassword) throws ValidationException {
        if (user == null) throw new ValidationException("User is required.");
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            throw new ValidationException("Old password is required.");
        }

        validatePassword(newPassword);

        String oldHash = hashPassword(oldPassword);
        if (!user.getPasswordHash().equals(oldHash)) {
            throw new ValidationException("Old password is incorrect.");
        }

        user.setPasswordHash(hashPassword(newPassword));
    }

    // UC-03 Update Preference
    public void updatePreference(User user, String newUserType) throws ValidationException {
        if (user == null) throw new ValidationException("User is required.");
        validateUserType(newUserType);

        user.setUserType(newUserType.toUpperCase());
    }

    // ---------- Validation methods ----------
    private void validateName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty.");
        }
    }

    private void validateEmail(String email) throws ValidationException {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email == null || !email.matches(emailRegex)) {
            throw new ValidationException("Invalid email format.");
        }
    }

    private void validatePassword(String password) throws ValidationException {
        if (password == null || password.length() < 6) {
            throw new ValidationException("Password must be at least 6 characters.");
        }
    }

    private void validatePhone(String phone) throws ValidationException {
        if (phone == null || phone.trim().isEmpty()) {
            throw new ValidationException("Phone cannot be empty.");
        }
        if (!phone.matches("^[0-9]{7,15}$")) {
            throw new ValidationException("Phone must be 7 to 15 digits.");
        }
    }

    private void validateAddress(String address) throws ValidationException {
        if (address == null || address.trim().isEmpty()) {
            throw new ValidationException("Address cannot be empty.");
        }
    }

    private void validateUserType(String userType) throws ValidationException {
        if (userType == null || !(userType.equalsIgnoreCase("FREE") || userType.equalsIgnoreCase("PREMIUM"))) {
            throw new ValidationException("User type must be FREE or PREMIUM.");
        }
    }

    private void checkDuplicateEmail(String email) throws ValidationException {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                throw new ValidationException("Email already registered.");
            }
        }
    }

    // ---------- Password Hash ----------
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error while hashing password.");
        }
    }
}