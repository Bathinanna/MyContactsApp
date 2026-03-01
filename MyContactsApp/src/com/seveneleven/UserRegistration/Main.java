package com.seveneleven.UserRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

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
            System.out.println("13. Bulk Delete Contacts (UC8)");
            System.out.println("14. Bulk Tag Contacts (UC8)");
            System.out.println("15. Bulk Export Contacts (UC8)");
            System.out.println("16. Search Contacts (UC9)");
            System.out.println("17. Basic Filtering (UC10)");
            System.out.println("18. Create Tag (UC11)");
            System.out.println("19. List Tags (UC11)");
            System.out.println("20. Assign Tag to Contact (UC11)");
            System.out.println("21. Remove Tag from Contact (UC11)");
            System.out.println("22. Exit");
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
                        if (myContacts.isEmpty()) System.out.println("No contacts found.");
                        else {
                            System.out.println("=== My Contacts ===");
                            for (Contact c : myContacts) System.out.println(c.getId() + " | " + c.getName() + " | Tags: " + c.getTags());
                        }
                        break;

                    case 10:
                        if (currentUser == null) { System.out.println("Please login first."); break; }
                        System.out.print("Enter Contact ID: ");
                        String contactId = sc.nextLine();
                        Optional<Contact> found = contactService.getContactByIdForUser(currentUser, contactId);
                        if (found.isPresent()) System.out.println("\n" + found.get());
                        else System.out.println("Contact not found.");
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
                        contactService.deleteContact(currentUser, deleteId);
                        System.out.println("Contact deleted successfully.");
                        break;

                    case 13:
                        if (currentUser == null) { System.out.println("Please login first."); break; }
                        System.out.print("How many contacts to delete? ");
                        int delCount = Integer.parseInt(sc.nextLine());
                        List<String> deleteIds = new ArrayList<>();
                        for (int i = 1; i <= delCount; i++) {
                            System.out.print("Contact ID " + i + ": ");
                            deleteIds.add(sc.nextLine());
                        }
                        int deleted = contactService.bulkDeleteContacts(currentUser, deleteIds);
                        System.out.println("Deleted contacts: " + deleted);
                        break;

                    case 14:
                        if (currentUser == null) { System.out.println("Please login first."); break; }
                        System.out.print("How many contacts to tag? ");
                        int tagCount = Integer.parseInt(sc.nextLine());
                        List<String> tagIds = new ArrayList<>();
                        for (int i = 1; i <= tagCount; i++) {
                            System.out.print("Contact ID " + i + ": ");
                            tagIds.add(sc.nextLine());
                        }
                        System.out.print("Enter tag name: ");
                        String bulkTag = sc.nextLine();
                        int tagged = contactService.bulkTagContacts(currentUser, tagIds, bulkTag);
                        System.out.println("Tagged contacts: " + tagged);
                        break;

                    case 15:
                        if (currentUser == null) { System.out.println("Please login first."); break; }
                        System.out.print("How many contacts to export? ");
                        int exportCount = Integer.parseInt(sc.nextLine());
                        List<String> exportIds = new ArrayList<>();
                        for (int i = 1; i <= exportCount; i++) {
                            System.out.print("Contact ID " + i + ": ");
                            exportIds.add(sc.nextLine());
                        }
                        System.out.print("Enter export CSV path: ");
                        String filePath = sc.nextLine();
                        String output = contactService.bulkExportContacts(currentUser, exportIds, filePath);
                        System.out.println("Exported to: " + output);
                        break;

                    case 16:
                        if (currentUser == null) { System.out.println("Please login first."); break; }

                        System.out.println("Search by: 1.Name 2.Phone 3.Email 4.Tag");
                        int searchType = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter keyword: ");
                        String keyword = sc.nextLine();

                        List<Contact> searchResult;
                        if (searchType == 1) searchResult = contactService.searchByName(currentUser, keyword);
                        else if (searchType == 2) searchResult = contactService.searchByPhone(currentUser, keyword);
                        else if (searchType == 3) searchResult = contactService.searchByEmail(currentUser, keyword);
                        else if (searchType == 4) searchResult = contactService.searchByTag(currentUser, keyword);
                        else { System.out.println("Invalid type."); break; }

                        if (searchResult.isEmpty()) System.out.println("No contacts found.");
                        else for (Contact c : searchResult) System.out.println(c.getId() + " | " + c.getName() + " | Tags: " + c.getTags());
                        break;

                    case 17:
                        if (currentUser == null) { System.out.println("Please login first."); break; }

                        System.out.println("Filter: 1.Tag 2.Date Added 3.Frequently Contacted");
                        int filterType = Integer.parseInt(sc.nextLine());
                        List<Contact> filtered;

                        if (filterType == 1) {
                            System.out.print("Enter tag keyword: ");
                            filtered = contactService.filterByTag(currentUser, sc.nextLine());
                        } else if (filterType == 2) {
                            System.out.print("Newest first? (yes/no): ");
                            filtered = contactService.filterByDateAdded(currentUser, sc.nextLine().equalsIgnoreCase("yes"));
                        } else if (filterType == 3) {
                            System.out.print("Recent interaction first? (yes/no): ");
                            filtered = contactService.filterByFrequentlyContacted(currentUser, sc.nextLine().equalsIgnoreCase("yes"));
                        } else {
                            System.out.println("Invalid filter type.");
                            break;
                        }

                        if (filtered.isEmpty()) System.out.println("No contacts found.");
                        else for (Contact c : filtered) System.out.println(c.getId() + " | " + c.getName() + " | Tags: " + c.getTags());
                        break;

                    case 18:
                        if (currentUser == null) { System.out.println("Please login first."); break; }
                        System.out.print("Enter new tag name: ");
                        Tag newTag = contactService.createTag(currentUser, sc.nextLine());
                        System.out.println("Tag created: " + newTag.getName());
                        break;

                    case 19:
                        if (currentUser == null) { System.out.println("Please login first."); break; }
                        Set<Tag> tags = contactService.getAllTags(currentUser);
                        if (tags.isEmpty()) System.out.println("No tags created yet.");
                        else {
                            System.out.println("=== All Tags ===");
                            for (Tag t : tags) System.out.println("- " + t.getName());
                        }
                        break;

                    case 20:
                        if (currentUser == null) { System.out.println("Please login first."); break; }
                        System.out.print("Enter Contact ID: ");
                        String assignContactId = sc.nextLine();
                        System.out.print("Enter Tag Name: ");
                        String assignTagName = sc.nextLine();
                        contactService.assignTagToContact(currentUser, assignContactId, assignTagName);
                        System.out.println("Tag assigned successfully.");
                        break;

                    case 21:
                        if (currentUser == null) { System.out.println("Please login first."); break; }
                        System.out.print("Enter Contact ID: ");
                        String removeContactId = sc.nextLine();
                        System.out.print("Enter Tag Name: ");
                        String removeTagName = sc.nextLine();
                        contactService.removeTagFromContact(currentUser, removeContactId, removeTagName);
                        System.out.println("Tag removed successfully.");
                        break;

                    case 22:
                        System.out.println("Exiting...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid option.");
                }
            } catch (ValidationException ve) {
                System.out.println("Validation error: " + ve.getMessage());
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }
}