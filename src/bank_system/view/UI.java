package bank_system.view;

public interface UI {
    void initializeUI();
    void showError(String message);
    void showSuccess(String message);
    void updateBalanceLabel();
}
