package com.seveneleven.UserRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static User currentUser = null; // simple session (OOP only)

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
            System.out.println("10. Exit");
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
                        if (currentUser == null) {
                            System.out.println("No user logged in.");
                        } else {
                            System.out.println("Current user: " + currentUser);
                        }
                        break;

                    case 4:
                        if (currentUser == null) {
                            System.out.println("Please login first.");
                            break;
                        }

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
                        if (currentUser == null) {
                            System.out.println("Please login first.");
                            break;
                        }

                        System.out.print("Old Password: ");
                        String oldPassword = sc.nextLine();

                        System.out.print("New Password: ");
                        String newPassword = sc.nextLine();

                        userService.changePassword(currentUser, oldPassword, newPassword);
                        System.out.println("Password changed successfully.");
                        break;

                    case 6:
                        if (currentUser == null) {
                            System.out.println("Please login first.");
                            break;
                        }

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
                        if (currentUser == null) {
                            System.out.println("Please login first.");
                            break;
                        }

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
                        System.out.println("Contact created successfully:");
                        System.out.println(created);
                        break;

                    case 9:
                        if (currentUser == null) {
                            System.out.println("Please login first.");
                            break;
                        }

                        List<Contact> myContacts = contactService.getContactsForUser(currentUser);
                        if (myContacts.isEmpty()) {
                            System.out.println("No contacts found.");
                        } else {
                            System.out.println("=== My Contacts ===");
                            for (Contact c : myContacts) {
                                System.out.println(c);
                            }
                        }
                        break;

                    case 10:
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