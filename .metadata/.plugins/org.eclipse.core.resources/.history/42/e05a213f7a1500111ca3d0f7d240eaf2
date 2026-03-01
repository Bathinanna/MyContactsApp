package com.seveneleven.UserRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactService {
    private List<Contact> contacts = new ArrayList<>();

    public Contact createContact(User loggedInUser, String name, List<String> phoneNumbers,
                                 List<String> emailAddresses, String company, String notes) throws ValidationException {
        if (loggedInUser == null) throw new ValidationException("Please login first.");

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

    public Optional<Contact> getContactByIdForUser(User loggedInUser, String contactId) {
        if (loggedInUser == null || contactId == null || contactId.trim().isEmpty()) return Optional.empty();

        for (Contact c : contacts) {
            if (c.getOwnerEmail().equalsIgnoreCase(loggedInUser.getEmail()) &&
                c.getId().equals(contactId.trim())) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
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

    public void editContact(User loggedInUser, String contactId, String newName,
                            List<String> newPhones, List<String> newEmails,
                            String newCompany, String newNotes) throws ValidationException {
        if (loggedInUser == null) throw new ValidationException("Please login first.");

        Contact contact = getContactByIdForUser(loggedInUser, contactId)
                .orElseThrow(() -> new ValidationException("Contact not found."));

        validateName(newName);
        validatePhoneList(newPhones);
        validateEmailList(newEmails);

        contact.setName(newName);
        contact.setPhoneNumbers(newPhones);
        contact.setEmailAddresses(newEmails);
        contact.setCompany(newCompany == null ? "" : newCompany);
        contact.setNotes(newNotes == null ? "" : newNotes);
    }

    // UC-07 Delete Contact (Hard Delete)
    public void deleteContact(User loggedInUser, String contactId) throws ValidationException {
        if (loggedInUser == null) {
            throw new ValidationException("Please login first.");
        }
        if (contactId == null || contactId.trim().isEmpty()) {
            throw new ValidationException("Contact ID is required.");
        }

        Contact contact = getContactByIdForUser(loggedInUser, contactId)
                .orElseThrow(() -> new ValidationException("Contact not found."));

        contacts.remove(contact);
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
        if (emails == null || emails.isEmpty()) return;

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