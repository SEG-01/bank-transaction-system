package bank_system.model;

import java.util.Random;

/**
 * User class represents a user in the bank system.
 * It contains user information such as username, password, bank account, and a unique user ID.
 */
public class User {
    private String username; // The username of the user
    private String password; // The password of the user
    private BankAccount account; // The bank account associated with the user
    private String user_id; // The unique user ID

    /**
     * Constructor to initialize the User with username, password, and bank account.
     * 
     * @param username the username of the user
     * @param password the password of the user
     * @param account the bank account associated with the user
     */
    public User(String username, String password, BankAccount account) {
        this.username = username;
        this.password = password;
        this.account = account;
        this.user_id = User.generateUniqueId(); // Generate a unique user ID
    }

    /**
     * Gets the username of the user.
     * 
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the bank account associated with the user.
     * 
     * @return the bank account associated with the user
     */
    public BankAccount account() {
        return account;
    }

    /**
     * Gets the unique user ID.
     * 
     * @return the unique user ID
     */
    public String getId() {
        return user_id;
    }

    /**
     * Generates a unique user ID based on the current time and a random number.
     * 
     * @return a unique user ID
     */
    private static String generateUniqueId() {
        long currentTimeMillis = System.currentTimeMillis();
        Random random = new Random();
        int randomNumber = random.nextInt(99999);
        String base = String.valueOf(currentTimeMillis) + String.valueOf(randomNumber);
        String uniqueId = base.substring(base.length() - 5);

        return uniqueId;
    }
}