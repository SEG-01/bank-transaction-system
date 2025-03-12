package bank_system.controller;

import bank_system.model.BankAccount;
import bank_system.model.User;
import java.util.HashMap;
import java.util.Map;

public class AuthController {
    private static final Map<String, User> users = new HashMap<>();
    private AuthController() {
    	
    }
    private static class SingletonHelper {
    	private static final AuthController INSTANCE = new AuthController();
    }
    
    public static AuthController getInstance() {
    	return SingletonHelper.INSTANCE;
    }
    
    public boolean register(String username, String password) {
        if (username.isEmpty() || password.isEmpty() || users.containsKey(username)) return false;
        users.put(username, new User(username, password, new BankAccount(0)));
        return true;
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
