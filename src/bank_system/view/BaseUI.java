package bank_system.view;

import javax.swing.*;

import bank_system.controller.TransactionController;
import bank_system.model.User;

/**
 * BaseUI class is an abstract class that provides common functionality for all UI components.
 * It implements the UI interface and provides methods to show error and success messages.
 */
public abstract class BaseUI implements UI {
    protected JFrame frame; // The main frame of the UI
    protected JLabel balanceLabel; // Label to display the user's balance
    protected TransactionController transaction_controller; // Controller to handle transactions
    protected User user; // The user associated with the UI

    /**
     * Default constructor for BaseUI.
     */
    public BaseUI() {}

    /**
     * Shows an error message dialog.
     * 
     * @param message the error message to be displayed
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows a success message dialog.
     * 
     * @param message the success message to be displayed
     */
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Abstract method to initialize the user interface.
     * Must be implemented by subclasses.
     */
    public abstract void initializeUI();

    /**
     * Abstract method to update the balance label.
     * Must be implemented by subclasses.
     */
    public abstract void updateBalanceLabel();
}