package com.seveneleven.UserRegistration;

public class User {
    private String name;
    private String email;
    private String passwordHash;
    private String phone;
    private String address;
    private String userType; // FREE or PREMIUM

    public User(String name, String email, String passwordHash, String phone, String address, String userType) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.address = address;
        this.userType = userType;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getUserType() { return userType; }

    // Setters (required by UserService UC3)
    public void setName(String name) { this.name = name; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setUserType(String userType) { this.userType = userType; }

    @Override
    public String toString() {
        return "User Registered Successfully!\n"
                + "Name: " + name + "\n"
                + "Email: " + email + "\n"
                + "Phone: " + phone + "\n"
                + "Address: " + address + "\n"
                + "Type: " + userType;
    }
}