package bank_system.tests;

import bank_system.controller.AuthController;
import bank_system.model.User;
import bank_system.model.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AuthControllerTest class contains unit tests for the AuthController class.
 * It tests the registration and login functionalities.
 */
public class AuthControllerTest {

    private AuthController authController;

    /**
     * Sets up the test environment before each test.
     * Initializes the AuthController instance and clears the users in UserManager.
     */
    @BeforeEach
    public void setUp() {
        authController = AuthController.getInstance();
        UserManager.clearUsers(); // Clear users for a clean test environment
    }

    /**
     * Tests successful registration of a new user.
     */
    @Test
    public void testRegisterSuccess() {
        boolean result = authController.register("newuser", "password");
        assertTrue(result);
        assertNotNull(UserManager.getUser("newuser"));
    }

    /**
     * Tests registration failure when the username is empty.
     */
    @Test
    public void testRegisterFailureEmptyUsername() {
        boolean result = authController.register("", "password");
        assertFalse(result);
    }

    /**
     * Tests registration failure when the password is empty.
     */
    @Test
    public void testRegisterFailureEmptyPassword() {
        boolean result = authController.register("newuser", "");
        assertFalse(result);
    }

    /**
     * Tests registration failure when the username already exists.
     */
    @Test
    public void testRegisterFailureUserExists() {
        authController.register("existinguser", "password");
        boolean result = authController.register("existinguser", "newpassword");
        assertFalse(result);
    }

    /**
     * Tests successful login with correct credentials.
     */
    @Test
    public void testLoginSuccess() {
        authController.register("newuser", "password");
        User user = authController.login("newuser", "password");
        assertNotNull(user);
        assertEquals("newuser", user.getUsername());
    }

    /**
     * Tests login failure with incorrect password.
     */
    @Test
    public void testLoginFailureWrongPassword() {
        authController.register("newuser", "password");
        User user = authController.login("newuser", "wrongpassword");
        assertNull(user);
    }

    /**
     * Tests login failure when the user does not exist.
     */
    @Test
    public void testLoginFailureUserNotExists() {
        User user = authController.login("nonexistentuser", "password");
        assertNull(user);
    }
}