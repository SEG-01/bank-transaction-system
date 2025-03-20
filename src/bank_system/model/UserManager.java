package bank_system.model;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final Map<String, User> users = new HashMap<>();
    
    private UserManager() {}
    
    public static void addUser(User user) {
        users.put(user.getUsername(), user);
    }
    
    public static User getUser(String username) {
        return users.get(username);
    }
    
    public static boolean userExists(String username) {
        return users.containsKey(username);
    }
    
    public static void clearUsers() {
        users.clear();
    }
}
