import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankTransactionSystemGUI {
    private static BankAccount account = new BankAccount(1000);

    public static void main(String[] args) {
        // Create a frame for the GUI
        JFrame frame = new JFrame("Bank Transaction System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create UI elements
        JPanel panel = new JPanel();
        JTextField depositField = new JTextField(10);
        JTextField withdrawField = new JTextField(10);
        JLabel balanceLabel = new JLabel("Balance: " + account.getBalance());

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton showTransactionsButton = new JButton("Show Transaction Log");

        // JTextArea to display transaction logs
        JTextArea transactionLogArea = new JTextArea(10, 40); // 10 rows, 40 columns
        transactionLogArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionLogArea); // Scroll pane for better display

        // Layout settings
        panel.setLayout(new GridLayout(4, 3)); // Increased rows to fit the new button
        panel.add(new JLabel("Deposit Amount:"));
        panel.add(depositField);
        panel.add(depositButton);
        panel.add(new JLabel("Withdraw Amount:"));
        panel.add(withdrawField);
        panel.add(withdrawButton);
        panel.add(balanceLabel);
        panel.add(showTransactionsButton);  // Add the new button
        panel.add(scrollPane);  // Add scroll pane for transaction log display

        frame.add(panel);
        frame.setVisible(true);

        // Action for deposit
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(depositField.getText());
                new Thread(() -> {
                    account.deposit(amount);
                    SwingUtilities.invokeLater(() -> balanceLabel.setText("Balance: " + account.getBalance()));
                }).start();
            }
        });
        
        // Action for withdraw
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(withdrawField.getText());
                new Thread(() -> {
                    account.withdraw(amount);
                    SwingUtilities.invokeLater(() -> balanceLabel.setText("Balance: " + account.getBalance()));
                }).start();
            }
        });

        // Action for showing transaction log
        showTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the JTextArea before displaying the updated log
                transactionLogArea.setText(""); 

                // Loop through each transaction in the account's log and add it to the JTextArea
                for (String transaction : account.getLog()) {
                    transactionLogArea.append(transaction + "\n");
                }
            }
        });
    }
}