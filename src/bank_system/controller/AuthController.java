package bank_system.controller;

import bank_system.model.User;
import bank_system.model.UserManager;
import bank_system.model.BankAccount;

/**
 * AuthController is responsible for handling user authentication and registration.
 * It follows the Singleton pattern to ensure only one instance is created.
 */
public class AuthController {
    
    // Private constructor to prevent instantiation
    private AuthController() {}

    // Static inner class to hold the singleton instance
    private static class SingletonHelper {
        private static final AuthController INSTANCE = new AuthController();
    }

    /**
     * Returns the singleton instance of AuthController.
     * 
     * @return the singleton instance of AuthController
     */
    public static AuthController getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Registers a new user with the given username and password.
     * 
     * @param username the username of the new user
     * @param password the password of the new user
     * @return true if registration is successful, false otherwise
     */
    public boolean register(String username, String password) {
        // Check if username or password is empty, or if the username already exists
        if (username.isEmpty() || password.isEmpty() || UserManager.userExists(username)) {
            return false;
        }
        // Create a new user with an initial bank account balance of 0
        User newUser = new User(username, password, new BankAccount(0));
        // Add the new user to the UserManager
        UserManager.addUser(newUser);
        return true;
    }

    /**
     * Logs in a user with the given username and password.
     * 
     * @param username the username of the user
     * @param password the password of the user
     * @return the User object if login is successful, null otherwise
     */
    public User login(String username, String password) {
        // Retrieve the user from the UserManager
        User user = UserManager.getUser(username);
        // Check if the user exists and the password matches
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}