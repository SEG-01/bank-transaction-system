package bank_system.view;

/**
 * BankTransactionSystemGUI class serves as the entry point for the bank transaction system's graphical user interface.
 * It initializes the UI by displaying the WelcomeUI.
 */
public class BankTransactionSystemGUI {

    /**
     * Constructor to initialize the BankTransactionSystemGUI.
     * Calls the initializeUI method to set up the user interface.
     */
    public BankTransactionSystemGUI() {
        this.initializeUI();
    }

    /**
     * Initializes the user interface by displaying the WelcomeUI.
     */
    private void initializeUI() {
        new WelcomeUI().initializeUI();
    }
}