package bank_system.tests;
import static org.junit.jupiter.api.Assertions.*;
import bank_system.constants.CurrencyConstants;
import bank_system.model.BankAccount;
import bank_system.model.TransactionResult;
import bank_system.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BankAccountTest {
    private User user1, user2;

    @BeforeEach
    void setUp() {
        user1 = new User("Alice", "1234", new BankAccount(500));
        user2 = new User("Bob", "1234", new BankAccount(300));
    }

    @Test
    void testDeposit_Successful() {
        TransactionResult result = user1.account().deposit(200);
        assertTrue(result.isSuccess());
        assertEquals(700, user1.account().getBalance());
        assertEquals("Deposit Successful:" + CurrencyConstants.POUND + "200.0", result.getMessage());
    }

    @Test
    void testDeposit_FailsWhenExceedingLimit() {
        TransactionResult result = user1.account().deposit(15000);
        assertFalse(result.isSuccess());
        assertEquals(500, user1.account().getBalance()); // Balance should remain unchanged
        assertEquals("Sorry, the deposit limit is" + CurrencyConstants.POUND + "10,000", result.getMessage());
    }

    @Test
    void testWithdraw_Successful() {
        TransactionResult result = user1.account().withdraw(100);
        assertTrue(result.isSuccess());
        assertEquals(400, user1.account().getBalance());
    }

    @Test
    void testTransferIn_Successful() {
        TransactionResult result = user1.account().transferIn(100, user2);
        assertTrue(result.isSuccess());
        assertEquals(600, user1.account().getBalance());
        assertEquals("Transferred In Successfully: 100.0", result.getMessage());
    }

    @Test
    void testTransferOut_Successful() {
        TransactionResult result = user1.account().transferOut(200, user2);
        assertTrue(result.isSuccess());
        assertEquals(300, user1.account().getBalance());
        assertEquals("Transferred out Successful: 200.0", result.getMessage());
    }

    @Test
    void testTransferOut_FailsDueToInsufficientFunds() {
        TransactionResult result = user1.account().transferOut(600, user2); // Exceeds balance
        assertFalse(result.isSuccess());
        assertEquals(500, user1.account().getBalance()); // Balance remains unchanged
    }
}
