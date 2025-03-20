package bank_system.tests;

import bank_system.controller.AuthController;
import bank_system.model.BankAccount;
import bank_system.model.User;
import bank_system.model.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest {

    private AuthController authController;

    @BeforeEach
    public void setUp() {
        authController = AuthController.getInstance();
        UserManager.clearUsers(); // Assuming there's a method to clear users for testing purposes
    }

    @Test
    public void testRegisterSuccess() {
        boolean result = authController.register("newuser", "password");
        assertTrue(result);
        assertNotNull(UserManager.getUser("newuser"));
    }

    @Test
    public void testRegisterFailureEmptyUsername() {
        boolean result = authController.register("", "password");
        assertFalse(result);
    }

    @Test
    public void testRegisterFailureEmptyPassword() {
        boolean result = authController.register("newuser", "");
        assertFalse(result);
    }

    @Test
    public void testRegisterFailureUserExists() {
        authController.register("existinguser", "password");
        boolean result = authController.register("existinguser", "newpassword");
        assertFalse(result);
    }

    @Test
    public void testLoginSuccess() {
        authController.register("newuser", "password");
        User user = authController.login("newuser", "password");
        assertNotNull(user);
        assertEquals("newuser", user.getUsername());
    }

    @Test
    public void testLoginFailureWrongPassword() {
        authController.register("newuser", "password");
        User user = authController.login("newuser", "wrongpassword");
        assertNull(user);
    }

    @Test
    public void testLoginFailureUserNotExists() {
        User user = authController.login("nonexistentuser", "password");
        assertNull(user);
    }
}