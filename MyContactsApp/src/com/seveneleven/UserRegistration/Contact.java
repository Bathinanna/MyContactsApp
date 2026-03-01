package com.seveneleven.UserRegistration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Contact {
    private String id;
    private String ownerEmail;
    private String name;
    private List<String> phoneNumbers;
    private List<String> emailAddresses;
    private String company;
    private String notes;
    private Set<Tag> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Contact(String ownerEmail, String name, List<String> phoneNumbers, List<String> emailAddresses,
                   String company, String notes) {
        this.id = UUID.randomUUID().toString();
        this.ownerEmail = ownerEmail;
        this.name = name;
        this.phoneNumbers = new ArrayList<>(phoneNumbers);
        this.emailAddresses = new ArrayList<>(emailAddresses);
        this.company = company;
        this.notes = notes;
        this.tags = new LinkedHashSet<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getOwnerEmail() { return ownerEmail; }
    public String getName() { return name; }
    public List<String> getPhoneNumbers() { return phoneNumbers; }
    public List<String> getEmailAddresses() { return emailAddresses; }
    public String getCompany() { return company; }
    public String getNotes() { return notes; }
    public Set<Tag> getTags() { return tags; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setName(String name) { this.name = name; this.updatedAt = LocalDateTime.now(); }
    public void setPhoneNumbers(List<String> phoneNumbers) { this.phoneNumbers = new ArrayList<>(phoneNumbers); this.updatedAt = LocalDateTime.now(); }
    public void setEmailAddresses(List<String> emailAddresses) { this.emailAddresses = new ArrayList<>(emailAddresses); this.updatedAt = LocalDateTime.now(); }
    public void setCompany(String company) { this.company = company; this.updatedAt = LocalDateTime.now(); }
    public void setNotes(String notes) { this.notes = notes; this.updatedAt = LocalDateTime.now(); }

    public void addTag(Tag tag) {
        if (tag != null) {
            tags.add(tag);
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void removeTag(Tag tag) {
        if (tag != null && tags.remove(tag)) {
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void addTags(Set<Tag> inputTags) {
        if (inputTags == null || inputTags.isEmpty()) return;
        for (Tag t : inputTags) {
            if (t != null) tags.add(t);
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void removeTags(Set<Tag> inputTags) {
        if (inputTags == null || inputTags.isEmpty()) return;
        for (Tag t : inputTags) {
            if (t != null) tags.remove(t);
        }
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
               "Tags: " + tags + "\n" +
               "Created At: " + createdAt + "\n" +
               "Updated At: " + updatedAt;
    }
}