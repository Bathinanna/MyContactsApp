package com.seveneleven.UserRegistration;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService();

        System.out.println("=== UC-01: User Registration ===");

        try {
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

            System.out.println("\n" + user);
            System.out.println("Password saved as hash: " + user.getPasswordHash());

        } catch (ValidationException e) {
            System.out.println("Registration failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}