package com.seveneleven.UserRegistration;

import java.util.ArrayList;
import java.util.List;

public class ContactService {
    private List<Contact> contacts = new ArrayList<>();

    // UC-04 Create Contact
    public Contact createContact(User loggedInUser,
                                 String name,
                                 List<String> phoneNumbers,
                                 List<String> emailAddresses,
                                 String company,
                                 String notes) throws ValidationException {
        if (loggedInUser == null) {
            throw new ValidationException("Please login first.");
        }

        validateName(name);
        validatePhoneList(phoneNumbers);
        validateEmailList(emailAddresses);

        Contact contact = new Contact(
                loggedInUser.getEmail(),
                name,
                phoneNumbers,
                emailAddresses,
                company == null ? "" : company,
                notes == null ? "" : notes
        );

        contacts.add(contact);
        return contact;
    }

    public List<Contact> getContactsForUser(User loggedInUser) {
        List<Contact> result = new ArrayList<>();
        if (loggedInUser == null) return result;

        for (Contact c : contacts) {
            if (c.getOwnerEmail().equalsIgnoreCase(loggedInUser.getEmail())) {
                result.add(c);
            }
        }
        return result;
    }

    private void validateName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Contact name is required.");
        }
    }

    private void validatePhoneList(List<String> phones) throws ValidationException {
        if (phones == null || phones.isEmpty()) {
            throw new ValidationException("At least one phone number is required.");
        }

        for (String p : phones) {
            if (p == null || p.trim().isEmpty()) {
                throw new ValidationException("Phone number cannot be empty.");
            }
            if (!p.matches("^[0-9]{7,15}$")) {
                throw new ValidationException("Invalid phone number: " + p);
            }
        }
    }

    private void validateEmailList(List<String> emails) throws ValidationException {
        if (emails == null || emails.isEmpty()) return; // optional

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        for (String e : emails) {
            if (e == null || e.trim().isEmpty()) {
                throw new ValidationException("Email cannot be empty.");
            }
            if (!e.matches(regex)) {
                throw new ValidationException("Invalid email: " + e);
            }
        }
    }
}