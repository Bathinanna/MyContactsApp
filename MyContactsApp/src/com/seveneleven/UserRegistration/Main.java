package com.seveneleven.UserRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static User currentUser = null;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService();
        ContactService contactService = new ContactService();

        while (true) {
            System.out.println("\n=== MyContacts App ===");
            System.out.println("1. Register (UC1)");
            System.out.println("2. Login (UC2)");
            System.out.println("3. Show Current User");
            System.out.println("4. Update Profile (UC3)");
            System.out.println("5. Change Password (UC3)");
            System.out.println("6. Update Preference (UC3)");
            System.out.println("7. Logout");
            System.out.println("8. Create Contact (UC4)");
            System.out.println("9. List My Contacts");
            System.out.println("10. View Contact Details (UC5)");
            System.out.println("11. Edit Contact (UC6)");
            System.out.println("12. Delete Contact (UC7)");
            System.out.println("13. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Enter valid number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter Password: ");
                        String password = sc.nextLine();
                        System.out.print("Enter Phone: ");
                        String phone = sc.nextLine();
                        System.out.print("Enter Address: ");
                        String address = sc.nextLine();
                        System.out.print("Enter User Type (FREE/PREMIUM): ");
                        String userType = sc.nextLine();

                        User user = userService.registerUser(name, email, password, phone, address, userType);
                        System.out.println("Registration successful for: " + user.getName());
                        break;

                    case 2:
                        System.out.print("Enter Email: ");
                        String loginEmail = sc.nextLine();
                        System.out.print("Enter Password: ");
                        String loginPassword = sc.nextLine();

                        Optional<User> loginResult = userService.findUserByEmailAndPassword(loginEmail, loginPassword);
                        if (loginResult.isPresent()) {
                            currentUser = loginResult.get();
                            System.out.println("Login successful. Welcome " + currentUser.getName());
                        } else {
                            System.out.println("Login failed. Invalid credentials.");
                        }
                        break;

                    case 3:
                        if (currentUser == null) System.out.println("No user logged in.");
                        else System.out.println("Current user: " + currentUser);
                        break;

                    case 4:
                        if (currentUser == null) { System.out.println("Please login first."); break; }

                        System.out.print("New Name: ");
                        String newName = sc.nextLine();
                        System.out.print("New Phone: ");
                        String newPhone = sc.nextLine();
                        System.out.print("New Address: ");
                        String newAddress = sc.nextLine();

                        userService.updateProfile(currentUser, newName, newPhone, newAddress);
                        System.out.println("Profile updated successfully.");
                        break;

                    case 5:
                        if (currentUser == null) { System.out.println("Please login first."); break; }

                        System.out.print("Old Password: ");
                        String oldPassword = sc.nextLine();
                        System.out.print("New Password: ");
                        String newPassword = sc.nextLine();

                        userService.changePassword(currentUser, oldPassword, newPassword);
                        System.out.println("Password changed successfully.");
                        break;

                    case 6:
                        if (currentUser == null) { System.out.println("Please login first."); break; }

                        System.out.print("New User Type (FREE/PREMIUM): ");
                        String newType = sc.nextLine();

                        userService.updatePreference(currentUser, newType);
                        System.out.println("Preference updated successfully.");
                        break;

                    case 7:
                        currentUser = null;
                        System.out.println("Logged out successfully.");
                        break;

                    case 8:
                        if (currentUser == null) { System.out.println("Please login first."); break; }

                        System.out.print("Contact Name: ");
                        String cName = sc.nextLine();

                        System.out.print("How many phone numbers? ");
                        int pCount = Integer.parseInt(sc.nextLine());
                        List<String> phones = new ArrayList<>();
                        for (int i = 1; i <= pCount; i++) {
                            System.out.print("Phone " + i + ": ");
                            phones.add(sc.nextLine());
                        }

                        System.out.print("How many emails? (0 allowed): ");
                        int eCount = Integer.parseInt(sc.nextLine());
                        List<String> emails = new ArrayList<>();
                        for (int i = 1; i <= eCount; i++) {
                            System.out.print("Email " + i + ": ");
                            emails.add(sc.nextLine());
                        }

                        System.out.print("Company (optional): ");
                        String company = sc.nextLine();
                        System.out.print("Notes (optional): ");
                        String notes = sc.nextLine();

                        Contact created = contactService.createContact(currentUser, cName, phones, emails, company, notes);
                        System.out.println("Contact created successfully. ID: " + created.getId());
                        break;

                    case 9:
                        if (currentUser == null) { System.out.println("Please login first."); break; }

                        List<Contact> myContacts = contactService.getContactsForUser(currentUser);
                        if (myContacts.isEmpty()) {
                            System.out.println("No contacts found.");
                        } else {
                            System.out.println("=== My Contacts ===");
                            for (Contact c : myContacts) {
                                System.out.println(c.getId() + " | " + c.getName());
                            }
                        }
                        break;

                    case 10:
                        if (currentUser == null) { System.out.println("Please login first."); break; }

                        System.out.print("Enter Contact ID: ");
                        String contactId = sc.nextLine();

                        Optional<Contact> found = contactService.getContactByIdForUser(currentUser, contactId);
                        if (found.isPresent()) {
                            System.out.println("\n Contact Details ");
                            System.out.println(found.get());
                        } else {
                            System.out.println("Contact not found.");
                        }
                        break;

                    case 11:
                        if (currentUser == null) { System.out.println("Please login first."); break; }

                        System.out.print("Enter Contact ID to edit: ");
                        String editId = sc.nextLine();

                        System.out.print("New Contact Name: ");
                        String editName = sc.nextLine();

                        System.out.print("How many phone numbers? ");
                        int editPCount = Integer.parseInt(sc.nextLine());
                        List<String> editPhones = new ArrayList<>();
                        for (int i = 1; i <= editPCount; i++) {
                            System.out.print("Phone " + i + ": ");
                            editPhones.add(sc.nextLine());
                        }

                        System.out.print("How many emails? (0 allowed): ");
                        int editECount = Integer.parseInt(sc.nextLine());
                        List<String> editEmails = new ArrayList<>();
                        for (int i = 1; i <= editECount; i++) {
                            System.out.print("Email " + i + ": ");
                            editEmails.add(sc.nextLine());
                        }

                        System.out.print("New Company (optional): ");
                        String editCompany = sc.nextLine();

                        System.out.print("New Notes (optional): ");
                        String editNotes = sc.nextLine();

                        contactService.editContact(currentUser, editId, editName, editPhones, editEmails, editCompany, editNotes);
                        System.out.println("Contact updated successfully.");
                        break;

                    case 12:
                        if (currentUser == null) { System.out.println("Please login first."); break; }

                        System.out.print("Enter Contact ID to delete: ");
                        String deleteId = sc.nextLine();

                        System.out.print("Are you sure you want to delete this contact? (yes/no): ");
                        String confirm = sc.nextLine();

                        if (!confirm.equalsIgnoreCase("yes")) {
                            System.out.println("Delete cancelled.");
                            break;
                        }

                        contactService.deleteContact(currentUser, deleteId);
                        System.out.println("Contact deleted successfully.");
                        break;

                    case 13:
                        System.out.println("Exiting...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (ValidationException e) {
                System.out.println("Validation error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }
}