package bank_system.model;

/**
 * TransactionResult represents the result of a transaction.
 * It contains information about whether the transaction was successful and a message describing the result.
 */
public class TransactionResult {
    private final boolean success; // Indicates whether the transaction was successful
    private final String message;  // Message describing the result of the transaction

    /**
     * Constructor to initialize the TransactionResult with success status and message.
     * 
     * @param success indicates whether the transaction was successful
     * @param message the message describing the result of the transaction
     */
    public TransactionResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Checks if the transaction was successful.
     * 
     * @return true if the transaction was successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gets the message describing the result of the transaction.
     * 
     * @return the message describing the result of the transaction
     */
    public String getMessage() {
        return message;
    }
}