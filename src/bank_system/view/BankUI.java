package bank_system.view;

import bank_system.constants.CurrencyConstants;
import bank_system.model.User;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * BankUI class represents the main user interface for the bank system.
 * It displays the user's account information and provides options for various transactions.
 */
public class BankUI extends BaseUI {
    private JLabel balanceLabel; // Label to display the user's balance
    private JLabel userIdLabel; // Label to display the user's account number

    /**
     * Constructor to initialize the BankUI with the given user.
     * 
     * @param user the user whose account information will be displayed
     */
    public BankUI(User user) {
        this.user = user;
    }

    /**
     * Initializes the user interface.
     * Sets up the frame and adds components such as labels, buttons, and action listeners.
     */
    @Override
    public void initializeUI() {
        frame = new JFrame("Bank System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        
        // Add Image (logo)
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/resources/logo.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));

        // Title
        JLabel titleLabel = new JLabel("Welcome, " + this.user.getUsername(), JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        
        userIdLabel = new JLabel("Account Number: " + this.user.getId());
        userIdLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        
        balanceLabel = new JLabel();
        this.updateBalanceLabel();
        balanceLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));

        JButton depositButton = new JButton("Make a Deposit");
        JButton transferButton = new JButton("Transfer Money");
        JButton historyButton = new JButton("Transaction History");
        JButton withdrawButton = new JButton("Make a Withdraw");
        JButton logOutButton = new JButton("Log Out");

        // Set up layout constraints and add components to the frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Logo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(logoLabel, gbc);

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        frame.add(titleLabel, gbc);
        
        // Balance Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        frame.add(balanceLabel, gbc);
        
        // User ID Label
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        frame.add(userIdLabel, gbc);

        // Reset insets and add buttons
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.ipady = 20;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(depositButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.ipady = 20;
        gbc.fill = GridBagConstraints.REMAINDER;
        frame.add(withdrawButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.ipady = 20;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(historyButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.ipady = 20;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(transferButton, gbc);

        // LogOut Button
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(logOutButton, gbc);

        frame.setVisible(true);

        // Add action listeners to buttons
        depositButton.addActionListener(e -> {
            frame.dispose();
            new DepositUI(this.user).initializeUI();
        });
        
        withdrawButton.addActionListener(e -> {
            frame.dispose();
            new WithdrawalUI(this.user).initializeUI();
        });

        logOutButton.addActionListener(e -> {
            frame.dispose();
            new LoginUI().initializeUI();
        });

        transferButton.addActionListener(e -> {
            frame.dispose();
            new TransfersUI(this.user).initializeUI();
        });

        historyButton.addActionListener(e -> {
            frame.dispose();
            new TransactionsHistoryUI(this.user).initializeUI();
        });
    }

    /**
     * Updates the balance label with the current balance of the user's account.
     */
    @Override
    public void updateBalanceLabel() {
        SwingUtilities.invokeLater(() -> {
            double balance = this.user.account().getBalance();
            DecimalFormat formatter = new DecimalFormat("#,###.##");
            String formattedBalance = "Balance:" + CurrencyConstants.POUND + formatter.format(balance);
            balanceLabel.setText(formattedBalance);
        });
    }
}