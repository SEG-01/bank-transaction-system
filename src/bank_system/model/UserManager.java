package bank_system.model;

import java.util.HashMap;
import java.util.Map;

/**
 * UserManager class manages the users in the bank system.
 * It provides methods to add, retrieve, check existence, and clear users.
 */
public class UserManager {
    private static final Map<String, User> users = new HashMap<>(); // Map to store users with their username as the key
    
    // Private constructor to prevent instantiation
    private UserManager() {}

    /**
     * Adds a user to the user manager.
     * 
     * @param user the user to be added
     */
    public static void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    /**
     * Retrieves a user by their username.
     * 
     * @param username the username of the user to be retrieved
     * @return the User object if found, null otherwise
     */
    public static User getUser(String username) {
        return users.get(username);
    }

    /**
     * Checks if a user exists by their username.
     * 
     * @param username the username to check for existence
     * @return true if the user exists, false otherwise
     */
    public static boolean userExists(String username) {
        return users.containsKey(username);
    }

    /**
     * Clears all users from the user manager.
     */
    public static void clearUsers() {
        users.clear();
    }
}