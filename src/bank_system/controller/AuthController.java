package bank_system.controller;

import bank_system.model.User;
import bank_system.model.UserManager;
import bank_system.model.BankAccount;

public class AuthController {
    private AuthController() {}
    
    private static class SingletonHelper {
        private static final AuthController INSTANCE = new AuthController();
    }
    
    public static AuthController getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    public boolean register(String username, String password) {
        if (username.isEmpty() || password.isEmpty() || UserManager.userExists(username)) {
            return false;
        }
        User newUser = new User(username, password, new BankAccount(0));
        UserManager.addUser(newUser);
        return true;
    }

    public User login(String username, String password) {
        User user = UserManager.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
