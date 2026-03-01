# MyContactsApp

A simple Java console-based contact management application built incrementally using **Use Cases (UC1–UC12)**.

---

##  Project Overview

MyContactsApp allows users to:

- Register and login
- Manage profile and preferences
- Create, view, edit, delete contacts
- Perform bulk operations
- Search and filter contacts
- Create and manage tags
- Apply one or multiple tags to contacts

This project demonstrates core **OOP concepts** and **Java collections** in a practical, beginner-friendly way.

---

##  Tech Stack

- **Language:** Java
- **Type:** Console Application
- **IDE:** Eclipse (recommended)
- **Data Storage:** In-memory (`ArrayList`, `Set`) in current version

---

##  Implemented Use Cases

## UC-01: User Registration
**Actor:** New User  
**Description:** Register with name, email, password, phone, address, user type (FREE/PREMIUM).  
**Concepts Used:**
- Validation methods
- Duplicate email check
- Password hashing

---

## UC-02: User Login
**Actor:** Registered User  
**Description:** Login using email and password.  
**Concepts Used:**
- Authentication
- `Optional<User>` for login result
- Password hash comparison

---

## UC-03: Update User Profile
**Actor:** Logged-in User  
**Description:** Update name, phone, address, password, preference.  
**Concepts Used:**
- Encapsulation (getters/setters)
- Re-validation of updated values

---

## UC-04: Create Contact
**Actor:** Logged-in User  
**Description:** Add contact with name, phone(s), email(s), company, notes.  
**Concepts Used:**
- `List<String>` for multiple phones/emails
- Input validation
- Contact object creation

---

## UC-05: View Contact Details
**Actor:** Logged-in User  
**Description:** View full details of a selected contact by ID.  
**Concepts Used:**
- Contact lookup by ID
- `Optional<Contact>`

---

## UC-06: Edit Contact
**Actor:** Logged-in User  
**Description:** Modify existing contact details.  
**Concepts Used:**
- Reuse validation logic
- Update object fields

---

## UC-07: Delete Contact
**Actor:** Logged-in User  
**Description:** Delete a contact using contact ID with confirmation.  
**Concepts Used:**
- Safe remove operation
- Ownership check (user can only manage own contacts)

---

## UC-08: Bulk Operations
**Actor:** Logged-in User  
**Description:**
- Bulk delete
- Bulk tag
- Bulk export to CSV

**Concepts Used:**
- Loops
- `Set<String>` to avoid duplicate IDs
- File writing using `FileWriter`

---

## UC-09: Contact Search
**Actor:** Logged-in User  
**Description:** Search contacts by:
- Name
- Phone
- Email
- Tag

**Concepts Used:**
- String matching (`contains`, case-insensitive search)
- Loop filtering

---

## UC-10: Basic Filtering
**Actor:** Logged-in User  
**Description:** Filter contacts by:
- Tag
- Date added
- Frequently contacted (using `updatedAt` proxy)

**Concepts Used:**
- `Comparator`
- `List.sort(...)`
- Date/time comparison

---

## UC-11: Create and Manage Tags
**Actor:** Logged-in User  
**Description:** Create reusable tags (Family, Work, Friends) and assign/remove tags to/from contacts.  
**Concepts Used:**
- `Tag` class
- Relationship: `Contact` ↔ `Tag`
- `Set<Tag>` for uniqueness
- Override `equals()` and `hashCode()`

---

## UC-12: Apply Tags to Contacts
**Actor:** Logged-in User  
**Description:** Assign or remove **multiple tags** on a contact in one action.  
**Concepts Used:**
- `add()` / `remove()` in collections
- Batch tag handling with loops
- Maintaining object relationships

---
