package bank_system;

import javax.swing.*;

import bank_system.view.BankTransactionSystemGUI;

public class AppInitializer {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(BankTransactionSystemGUI::new);
	}
}
