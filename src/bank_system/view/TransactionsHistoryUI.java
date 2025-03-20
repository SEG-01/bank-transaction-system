package bank_system.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import bank_system.model.User;
import bank_system.model.BankAccount;
import org.json.JSONObject;

import javax.swing.table.DefaultTableModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

/**
 * TransactionsHistoryUI class represents the user interface for viewing the transaction history.
 * It extends the BaseUI class and provides functionality to display the user's transaction log.
 */
public class TransactionsHistoryUI extends BaseUI {
    private JLabel transactionLabel; // Label to display the title "Transaction Log"
    private JTable transactionTable; // Table to display the transaction log
    private DefaultTableModel tableModel; // Table model to manage the data in the table
    private JButton backButton; // Button to go back to the main bank UI
    private BankAccount account; // The bank account associated with the user

    /**
     * Constructor to initialize the TransactionsHistoryUI with the given user.
     * 
     * @param user the user whose transaction history will be displayed
     */
    public TransactionsHistoryUI(User user) {
        this.user = user;
        this.account = this.user.account();
    }

    /**
     * Initializes the user interface for viewing the transaction history.
     * Sets up the frame and adds components such as labels, table, and buttons.
     */
    public void initializeUI() {
        frame = new JFrame("Transactions");
        frame.setSize(600, 400);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        transactionLabel = new JLabel("Transaction Log", SwingConstants.CENTER);
        frame.add(transactionLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Type", "Amount", "Balance", "Time"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Back Button
        backButton = new JButton("Back to Bank");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newBankUI(user);
            }
        });

        frame.add(backButton, BorderLayout.SOUTH);
        frame.setVisible(true);

        updateTransactionList();  // Populate the table with transaction data
    }

    /**
     * Updates the transaction list by populating the table with transaction data.
     * Clears the table before adding new rows.
     */
    private void updateTransactionList() {
        tableModel.setRowCount(0);  // Clear the table before adding new rows
        List<JSONObject> transactions = account.getLog();  // Get the transaction log

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  // Time format

        // Loop through each JSON and add it as a row in the table
        for (JSONObject transaction : transactions) {
            // Convert time to readable format
            long timeInMillis = transaction.optLong("Time");
            String formattedTime = dateFormat.format(new Date(timeInMillis));  

            // Add each transaction as a row with the respective columns
            Object[] row = new Object[]{
                transaction.optString("Type"), 
                transaction.optDouble("Amount"), 
                transaction.optDouble("Balance"), 
                formattedTime
            };
            tableModel.addRow(row);  // Add the row to the table
        }
    }

    /**
     * Opens the main bank UI.
     * 
     * @param user the user whose bank UI will be opened
     */
    private void newBankUI(User user) {
        frame.dispose();
        new BankUI(this.user).initializeUI();
    }

    /**
     * Updates the balance label. This method is not used in TransactionsHistoryUI.
     */
    @Override
    public void updateBalanceLabel() {}
}