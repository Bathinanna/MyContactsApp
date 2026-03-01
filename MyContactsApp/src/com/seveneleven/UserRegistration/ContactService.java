package com.seveneleven.UserRegistration;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ContactService {
    private List<Contact> contacts = new ArrayList<>();

    // UC-04 Create Contact
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

    // UC-05 View Contact Details
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

    // List all contacts for logged-in user
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

    // UC-06 Edit Contact
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
        if (loggedInUser == null) throw new ValidationException("Please login first.");
        if (contactId == null || contactId.trim().isEmpty()) throw new ValidationException("Contact ID is required.");

        Contact contact = getContactByIdForUser(loggedInUser, contactId)
                .orElseThrow(() -> new ValidationException("Contact not found."));

        contacts.remove(contact);
    }

    // UC-08 Bulk Delete
    public int bulkDeleteContacts(User loggedInUser, List<String> contactIds) throws ValidationException {
        if (loggedInUser == null) throw new ValidationException("Please login first.");
        if (contactIds == null || contactIds.isEmpty()) throw new ValidationException("No contact IDs provided.");

        Set<String> idSet = new LinkedHashSet<>();
        for (String id : contactIds) {
            if (id != null && !id.trim().isEmpty()) idSet.add(id.trim());
        }
        if (idSet.isEmpty()) throw new ValidationException("No valid IDs.");

        int deletedCount = 0;
        List<Contact> toRemove = new ArrayList<>();

        for (Contact c : contacts) {
            if (c.getOwnerEmail().equalsIgnoreCase(loggedInUser.getEmail()) && idSet.contains(c.getId())) {
                toRemove.add(c);
            }
        }

        for (Contact c : toRemove) {
            contacts.remove(c);
            deletedCount++;
        }

        return deletedCount;
    }

    // UC-08 Bulk Tag (stored in notes as text)
    public int bulkTagContacts(User loggedInUser, List<String> contactIds, String tag) throws ValidationException {
        if (loggedInUser == null) throw new ValidationException("Please login first.");
        if (tag == null || tag.trim().isEmpty()) throw new ValidationException("Tag cannot be empty.");
        if (contactIds == null || contactIds.isEmpty()) throw new ValidationException("No contact IDs provided.");

        Set<String> idSet = new LinkedHashSet<>();
        for (String id : contactIds) {
            if (id != null && !id.trim().isEmpty()) idSet.add(id.trim());
        }

        int updated = 0;
        for (Contact c : contacts) {
            if (c.getOwnerEmail().equalsIgnoreCase(loggedInUser.getEmail()) && idSet.contains(c.getId())) {
                String oldNotes = c.getNotes() == null ? "" : c.getNotes();
                String newNotes = oldNotes.isBlank() ? ("Tag: " + tag) : (oldNotes + " | Tag: " + tag);
                c.setNotes(newNotes);
                updated++;
            }
        }
        return updated;
    }

    // UC-08 Bulk Export to CSV
    public String bulkExportContacts(User loggedInUser, List<String> contactIds, String filePath) throws ValidationException {
        if (loggedInUser == null) throw new ValidationException("Please login first.");
        if (filePath == null || filePath.trim().isEmpty()) throw new ValidationException("File path is required.");
        if (contactIds == null || contactIds.isEmpty()) throw new ValidationException("No contact IDs provided.");

        Set<String> idSet = new LinkedHashSet<>();
        for (String id : contactIds) {
            if (id != null && !id.trim().isEmpty()) idSet.add(id.trim());
        }

        List<Contact> selected = new ArrayList<>();
        for (Contact c : contacts) {
            if (c.getOwnerEmail().equalsIgnoreCase(loggedInUser.getEmail()) && idSet.contains(c.getId())) {
                selected.add(c);
            }
        }

        if (selected.isEmpty()) throw new ValidationException("No matching contacts found.");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("id,name,phones,emails,company,notes\n");
            for (Contact c : selected) {
                writer.write(csv(c.getId()) + "," +
                             csv(c.getName()) + "," +
                             csv(String.join(";", c.getPhoneNumbers())) + "," +
                             csv(String.join(";", c.getEmailAddresses())) + "," +
                             csv(c.getCompany()) + "," +
                             csv(c.getNotes()) + "\n");
            }
        } catch (IOException e) {
            throw new ValidationException("Export failed: " + e.getMessage());
        }

        return filePath;
    }

    // UC-09 Search by Name
    public List<Contact> searchByName(User loggedInUser, String keyword) throws ValidationException {
        if (loggedInUser == null) throw new ValidationException("Please login first.");
        if (keyword == null || keyword.trim().isEmpty()) throw new ValidationException("Name keyword is required.");

        List<Contact> result = new ArrayList<>();
        String key = keyword.trim().toLowerCase();

        for (Contact c : contacts) {
            if (!c.getOwnerEmail().equalsIgnoreCase(loggedInUser.getEmail())) continue;
            String name = c.getName() == null ? "" : c.getName();
            if (name.toLowerCase().contains(key)) result.add(c);
        }
        return result;
    }

    // UC-09 Search by Phone
    public List<Contact> searchByPhone(User loggedInUser, String phoneKeyword) throws ValidationException {
        if (loggedInUser == null) throw new ValidationException("Please login first.");
        if (phoneKeyword == null || phoneKeyword.trim().isEmpty()) throw new ValidationException("Phone keyword is required.");

        List<Contact> result = new ArrayList<>();
        String key = phoneKeyword.trim();

        for (Contact c : contacts) {
            if (!c.getOwnerEmail().equalsIgnoreCase(loggedInUser.getEmail())) continue;

            for (String p : c.getPhoneNumbers()) {
                if (p != null && p.contains(key)) {
                    result.add(c);
                    break;
                }
            }
        }
        return result;
    }

    // UC-09 Search by Email
    public List<Contact> searchByEmail(User loggedInUser, String emailKeyword) throws ValidationException {
        if (loggedInUser == null) throw new ValidationException("Please login first.");
        if (emailKeyword == null || emailKeyword.trim().isEmpty()) throw new ValidationException("Email keyword is required.");

        List<Contact> result = new ArrayList<>();
        String key = emailKeyword.trim().toLowerCase();

        for (Contact c : contacts) {
            if (!c.getOwnerEmail().equalsIgnoreCase(loggedInUser.getEmail())) continue;

            for (String e : c.getEmailAddresses()) {
                if (e != null && e.toLowerCase().contains(key)) {
                    result.add(c);
                    break;
                }
            }
        }
        return result;
    }

    // UC-09 Search by Tag (stored in notes)
    public List<Contact> searchByTag(User loggedInUser, String tagKeyword) throws ValidationException {
        if (loggedInUser == null) throw new ValidationException("Please login first.");
        if (tagKeyword == null || tagKeyword.trim().isEmpty()) throw new ValidationException("Tag keyword is required.");

        List<Contact> result = new ArrayList<>();
        String key = tagKeyword.trim().toLowerCase();

        for (Contact c : contacts) {
            if (!c.getOwnerEmail().equalsIgnoreCase(loggedInUser.getEmail())) continue;
            String notes = c.getNotes() == null ? "" : c.getNotes();
            if (notes.toLowerCase().contains(key)) result.add(c);
        }
        return result;
    }

    // UC-10 Filter by Tag
    public List<Contact> filterByTag(User loggedInUser, String tagKeyword) throws ValidationException {
        return searchByTag(loggedInUser, tagKeyword);
    }

    // UC-10 Filter by Date Added (createdAt)
    public List<Contact> filterByDateAdded(User loggedInUser, boolean newestFirst) throws ValidationException {
        if (loggedInUser == null) throw new ValidationException("Please login first.");

        List<Contact> result = getContactsForUser(loggedInUser);

        result.sort((a, b) -> newestFirst
                ? b.getCreatedAt().compareTo(a.getCreatedAt())
                : a.getCreatedAt().compareTo(b.getCreatedAt()));

        return result;
    }

    // UC-10 Filter by Frequently Contacted (proxy using updatedAt)
    public List<Contact> filterByFrequentlyContacted(User loggedInUser, boolean recentFirst) throws ValidationException {
        if (loggedInUser == null) throw new ValidationException("Please login first.");

        List<Contact> result = getContactsForUser(loggedInUser);

        result.sort((a, b) -> recentFirst
                ? b.getUpdatedAt().compareTo(a.getUpdatedAt())
                : a.getUpdatedAt().compareTo(b.getUpdatedAt()));

        return result;
    }

    // ---------- Helpers ----------
    private String csv(String value) {
        if (value == null) return "\"\"";
        return "\"" + value.replace("\"", "\"\"") + "\"";
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