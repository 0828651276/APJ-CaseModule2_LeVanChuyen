public class CheckingAccount extends Account {
    private static final long serialVersionUID = 1L;
    private String cardNumber;
    private double accountBalance;

    public CheckingAccount(int id, String accountCode, String accountName, String creationDate,
                           String cardNumber, double accountBalance) {
        super(id, accountCode, accountName, creationDate);
        this.cardNumber = cardNumber;
        this.accountBalance = accountBalance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return super.toString() + ", Số thẻ: " + cardNumber + ", Số tiền trong tài khoản: " + accountBalance;
    }
}
