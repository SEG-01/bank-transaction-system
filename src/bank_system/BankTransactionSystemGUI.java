package bank_system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main class for GUI
public class BankTransactionSystemGUI {
    private JFrame frame;
    private JTextField depositField, withdrawField;
    private JLabel welcomeLabel;
    private JLabel balanceLabel;
    private BankAccount account;
    private TransactionHandler transactionHandler;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public BankTransactionSystemGUI() {
        account = new BankAccount(1000);
        transactionHandler = new TransactionHandler(account, this);
        initializeUI();
    }

    private void initializeUI() {
        // Set up the frame
        frame = new JFrame("Bank Transaction System");
        frame.setSize(550, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up CardLayout for different screens
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Main screen setup
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Bigger font for welcome label

        // Center welcome label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        mainPanel.add(welcomeLabel, gbc);

        // Login and Register buttons with bigger size
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 50));
        loginButton.addActionListener(e -> cardLayout.show(cardPanel, "Transaction Screen"));
        gbc.gridy = 1;
        mainPanel.add(loginButton, gbc);

        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(200, 50));
        registerButton.addActionListener(e -> cardLayout.show(cardPanel, "Transaction Screen"));
        gbc.gridy = 2;
        mainPanel.add(registerButton, gbc);

        // Transaction screen setup
        JPanel transactionPanel = new JPanel(new GridLayout(4, 3));
        depositField = new JTextField(10);
        withdrawField = new JTextField(10);
        balanceLabel = new JLabel("Balance: £" + account.getBalance());

        JButton depositButton = new JButton("Deposit");
        depositButton.setPreferredSize(new Dimension(200, 50));
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setPreferredSize(new Dimension(200, 50));
        JButton showTransactionsButton = new JButton("Show Transaction Log");
        showTransactionsButton.setPreferredSize(new Dimension(200, 50));

        JTextArea transactionLogArea = new JTextArea(10, 40);
        transactionLogArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionLogArea);

        // Add components to transaction panel
        transactionPanel.add(new JLabel("Deposit Amount:"));
        transactionPanel.add(depositField);
        transactionPanel.add(depositButton);
        transactionPanel.add(new JLabel("Withdraw Amount:"));
        transactionPanel.add(withdrawField);
        transactionPanel.add(withdrawButton);
        transactionPanel.add(balanceLabel);
        transactionPanel.add(showTransactionsButton);
        transactionPanel.add(scrollPane);

        // Add panels to cardPanel
        cardPanel.add(mainPanel, "Main Screen");
        cardPanel.add(transactionPanel, "Transaction Screen");

        // Add cardPanel to frame
        frame.add(cardPanel);
        frame.setVisible(true);

        // Show transaction log when button is clicked
        showTransactionsButton.addActionListener(e -> {
            transactionLogArea.setText("");
            for (String transaction : account.getLog()) {
                transactionLogArea.append(transaction + "\n");
            }
        });

        // Attach event handlers for deposit/withdraw buttons
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


