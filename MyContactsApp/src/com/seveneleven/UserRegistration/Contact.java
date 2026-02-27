package com.seveneleven.UserRegistration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Contact {
    private String id;
    private String ownerEmail;
    private String name;
    private List<String> phoneNumbers;
    private List<String> emailAddresses;
    private String company;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Contact(String ownerEmail, String name, List<String> phoneNumbers, List<String> emailAddresses,
                   String company, String notes) {
        this.id = UUID.randomUUID().toString();
        this.ownerEmail = ownerEmail;
        this.name = name;
        this.phoneNumbers = new ArrayList<>(phoneNumbers);      // defensive copy
        this.emailAddresses = new ArrayList<>(emailAddresses);  // defensive copy
        this.company = company;
        this.notes = notes;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Copy constructor (deep copy style for lists)
    public Contact(Contact other) {
        this.id = other.id;
        this.ownerEmail = other.ownerEmail;
        this.name = other.name;
        this.phoneNumbers = new ArrayList<>(other.phoneNumbers);
        this.emailAddresses = new ArrayList<>(other.emailAddresses);
        this.company = other.company;
        this.notes = other.notes;
        this.createdAt = other.createdAt;
        this.updatedAt = other.updatedAt;
    }

    public String getId() { return id; }
    public String getOwnerEmail() { return ownerEmail; }
    public String getName() { return name; }
    public List<String> getPhoneNumbers() { return new ArrayList<>(phoneNumbers); }      // defensive copy
    public List<String> getEmailAddresses() { return new ArrayList<>(emailAddresses); }   // defensive copy
    public String getCompany() { return company; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = new ArrayList<>(phoneNumbers); // defensive copy
        this.updatedAt = LocalDateTime.now();
    }

    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = new ArrayList<>(emailAddresses); // defensive copy
        this.updatedAt = LocalDateTime.now();
    }

    public void setCompany(String company) {
        this.company = company;
        this.updatedAt = LocalDateTime.now();
    }

    public void setNotes(String notes) {
        this.notes = notes;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Contact ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Phone Numbers: " + phoneNumbers + "\n" +
                "Email Addresses: " + emailAddresses + "\n" +
                "Company: " + (company == null || company.isBlank() ? "N/A" : company) + "\n" +
                "Notes: " + (notes == null || notes.isBlank() ? "N/A" : notes) + "\n" +
                "Created At: " + createdAt + "\n" +
                "Updated At: " + updatedAt;
    }
}