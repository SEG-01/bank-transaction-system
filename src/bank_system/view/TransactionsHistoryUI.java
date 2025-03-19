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

public class TransactionsHistoryUI extends BaseUI{
    private JFrame frame;
    private JLabel transactionLabel;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private User user;
    private BankAccount account;

    public TransactionsHistoryUI(User user) {
        this.user = user;
        this.account = this.user.account();
    }

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

    private void updateTransactionList() {
        tableModel.setRowCount(0);  // Clear the table before adding new rows
        List<JSONObject> transactions = account.getLog();  // Get the transaction log

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  // Time FOrmat

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

    private void newBankUI(User user) {
        frame.dispose();
        new BankUI(this.user).initializeUI();
    }

    @Override
    public void updateBalanceLabel() {}
}

