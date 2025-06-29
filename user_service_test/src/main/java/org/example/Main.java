package org.example;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.util.HibernateUtil;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final UserDao userDao = new UserDaoImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        logger.info("Application started");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        createUser();
                        break;
                    case 2:
                        getUserById();
                        break;
                    case 3:
                        getAllUsers();
                        break;
                    case 4:
                        updateUser();
                        break;
                    case 5:
                        deleteUser();
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                logger.error("Error occurred: ", e);
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        logger.info("Application shutting down");
        HibernateUtil.shutdown();
    }

    private static void printMenu() {
        System.out.println("\nUser Service Menu:");
        System.out.println("1. Create User");
        System.out.println("2. Get User by ID");
        System.out.println("3. Get All Users");
        System.out.println("4. Update User");
        System.out.println("5. Delete User");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void createUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        User user = new User(name, email, age);
        userDao.save(user);
        System.out.println("User created successfully: " + user);
        logger.info("Created user: {}", user);
    }

    private static void getUserById() {
        System.out.print("Enter user ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        User user = userDao.findById(id);
        if (user != null) {
            System.out.println("User found: " + user);
        } else {
            System.out.println("User not found with ID: " + id);
        }
    }

    private static void getAllUsers() {
        List<User> users = userDao.findAll();
        if (users.isEmpty()) {
            System.out.println("No users found");
        } else {
            System.out.println("List of users:");
            users.forEach(System.out::println);
        }
    }

    private static void updateUser() {
        System.out.print("Enter user ID to update: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        User user = userDao.findById(id);
        if (user == null) {
            System.out.println("User not found with ID: " + id);
            return;
        }

        System.out.print("Enter new name (current: " + user.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            user.setName(name);
        }

        System.out.print("Enter new email (current: " + user.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            user.setEmail(email);
        }

        System.out.print("Enter new age (current: " + user.getAge() + "): ");
        String ageInput = scanner.nextLine();
        if (!ageInput.isEmpty()) {
            user.setAge(Integer.parseInt(ageInput));
        }

        userDao.update(user);
        System.out.println("User updated successfully: " + user);
        logger.info("Updated user: {}", user);
    }

    private static void deleteUser() {
        System.out.print("Enter user ID to delete: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        User user = userDao.findById(id);
        if (user == null) {
            System.out.println("User not found with ID: " + id);
            return;
        }

        userDao.delete(user);
        System.out.println("User deleted successfully with ID: " + id);
        logger.info("Deleted user with ID: {}", id);
    }
}