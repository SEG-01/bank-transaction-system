package bank_system.view;

/**
 * UI interface represents the user interface for the bank system.
 * It provides methods to initialize the UI, show error and success messages, and update the balance label.
 */
public interface UI {
    
    /**
     * Initializes the user interface.
     */
    void initializeUI();

    /**
     * Shows an error message.
     * 
     * @param message the error message to be displayed
     */
    void showError(String message);

    /**
     * Shows a success message.
     * 
     * @param message the success message to be displayed
     */
    void showSuccess(String message);

    /**
     * Updates the balance label.
     */
    void updateBalanceLabel();
}