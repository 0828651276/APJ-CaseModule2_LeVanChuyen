import java.io.Serializable;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int id;
    protected String accountCode;
    protected String accountName;
    protected String creationDate;

    public Account(int id, String accountCode, String accountName, String creationDate) {
        this.id = id;
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Id: "+ id + ", Mã tài khoản: " + accountCode + ", Tên tài khoản: " + accountName + ", Ngày tạo: " + creationDate;
    }
}

