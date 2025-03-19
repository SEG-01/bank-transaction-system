package bank_system.model;

import java.util.Random;

public class User {
    private String username;
    private String password;
    private BankAccount account;
    private String user_id;

    public User(String username, String password, BankAccount account) {
        this.username = username;
        this.password = password;
        this.account = account;
        this.user_id = User.generateUniqueId();
    }
    

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public BankAccount account() {
    	return account;
    }
    
    public String getId() {
    	return user_id;
    }
    
    private static String generateUniqueId() {
        long currentTimeMillis = System.currentTimeMillis();
        Random random = new Random();
        int randomNumber = random.nextInt(99999);
        String base = String.valueOf(currentTimeMillis) + String.valueOf(randomNumber);
        String uniqueId = base.substring(base.length() - 5);

        return uniqueId;
    }
    
}
