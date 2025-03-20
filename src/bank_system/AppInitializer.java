package bank_system;

import javax.swing.*;

import bank_system.view.BankTransactionSystemGUI;

/**
 * AppInitializer class serves as the entry point for the bank transaction system application.
 * It initializes the graphical user interface by invoking the BankTransactionSystemGUI.
 */
public class AppInitializer {
    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread: creating and showing the application's GUI.
        SwingUtilities.invokeLater(BankTransactionSystemGUI::new);
    }
}