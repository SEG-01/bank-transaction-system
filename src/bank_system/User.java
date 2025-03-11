package bank_system;
class User {
    private int id;
    private int uniqueId;
    private String username;
    private String password;
    private String currency;
    private int balance;
    private String userType;
    private BankAccount account;

    public User(int uniqueId, String username, String password, String currency, int balance, String userType) {
        this.uniqueId = uniqueId;
        this.username = username;
        this.password = password;
        this.currency = currency;
        this.balance = balance;
        this.userType = userType;
        this.account = new BankAccount(balance);
    }

    public BankAccount getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uniqueId=" + uniqueId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                ", userType='" + userType + '\'' +
                ", account=" + account +
                '}';
    }
}
