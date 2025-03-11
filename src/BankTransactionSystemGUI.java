import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main class for GUI
public class BankTransactionSystemGUI {
    private JFrame frame;
    private JTextField depositField, withdrawField;
    private JLabel balanceLabel;
    private BankAccount account;
    private TransactionHandler transactionHandler;

    public BankTransactionSystemGUI() {
        account = new BankAccount(1000);
        transactionHandler = new TransactionHandler(account, this);
        initializeUI();
    }

    private void initializeUI() {
        // Create a frame for the GUI
        frame = new JFrame("Bank Transaction System");
        frame.setSize(550, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create UI elements
        JPanel panel = new JPanel(new GridLayout(3, 4));
        depositField = new JTextField(10);
        withdrawField = new JTextField(10);
        balanceLabel = new JLabel("Balance: £" + account.getBalance());

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");

        // Add components to the panel
        panel.add(new JLabel("Deposit Amount:"));
        panel.add(depositField);
        panel.add(depositButton);
        panel.add(new JLabel("Withdraw Amount:"));
        panel.add(withdrawField);
        panel.add(withdrawButton);
        panel.add(balanceLabel);

        frame.add(panel);
        frame.setVisible(true);

        // Attach event handlers
        depositButton.addActionListener(e -> transactionHandler.handleDeposit(depositField));
        withdrawButton.addActionListener(e -> transactionHandler.handleWithdrawal(withdrawField));
    }

    // Update balance label
    public void updateBalanceLabel() {
        SwingUtilities.invokeLater(() -> balanceLabel.setText("Balance: £" + account.getBalance()));
    }

    // Show error messages
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    // Show success messages
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankTransactionSystemGUI::new);
    }
}
