public class SavingAccount extends Account {
    private static final long serialVersionUID = 1L;
    private double depositAmount;
    private String depositDate;
    private double interestRate;
    private int term;

    public SavingAccount(int id, String accountCode, String accountName, String creationDate,
                         double depositAmount, String depositDate, double interestRate, int term) {
        super(id, accountCode, accountName, creationDate);
        this.depositAmount = depositAmount;
        this.depositDate = depositDate;
        this.interestRate = interestRate;
        this.term = term;
    }

    @Override
    public String toString() {
        return super.toString() + ", Số tiền gửi: " + depositAmount + ", Ngày gửi: " + depositDate + ", Lãi suất: " + interestRate + ", Kì hạn: " + term + " tháng";
    }
}



