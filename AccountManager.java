import java.io.*;
import java.util.*;

public class AccountManager {
    private static final String FILE_PATH = "accounts.csv";
    private List<Account> accounts = new ArrayList<>();
    private int idCounter = 1;

    public AccountManager() {
        loadAccounts();
    }

    private void loadAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String accountType = parts[4];

                if (accountType.equals("S")) {
                    accounts.add(new SavingAccount(id, parts[1], parts[2], parts[3],
                            Double.parseDouble(parts[5]), parts[6],
                            Double.parseDouble(parts[7]), Integer.parseInt(parts[8])));
                } else if (accountType.equals("C")) {
                    accounts.add(new CheckingAccount(id, parts[1], parts[2], parts[3],
                            parts[5], Double.parseDouble(parts[6])));
                }

                idCounter = Math.max(idCounter, id + 1);
            }
        } catch (IOException e) {
        }
    }

    private void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Account account : accounts) {
                writer.write(account.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    private boolean isPositiveDouble(String str) {
        try {
            double value = Double.parseDouble(str);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.length() == 12 && cardNumber.matches("0[0-9]{11}") && !isCardNumberUsed(cardNumber);
    }

    private boolean isCardNumberUsed(String cardNumber) {
        for (Account account : accounts) {
            if (account instanceof CheckingAccount) {
                CheckingAccount checkingAccount = (CheckingAccount) account;
                if (checkingAccount.getCardNumber().equals(cardNumber)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAccountCodeDuplicate(String accountCode) {
        for (Account account : accounts) {
            if (account.getAccountCode().equalsIgnoreCase(accountCode)) {
                return true;
            }
        }
        return false;
    }


    public void addAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chọn loại tài khoản: 1. Tiết kiệm 2. Thanh toán");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String accountCode;
        while (true) {
            System.out.print("Nhập mã tài khoản: ");
            accountCode = scanner.nextLine();
            if (!isAccountCodeDuplicate(accountCode)) {
                break;
            } else {
                System.out.println("Mã tài khoản đã tồn tại. Vui lòng nhập mã khác.");
            }
        }

        System.out.print("Nhập tên chủ tài khoản: ");
        String accountHolderName = scanner.nextLine();
        System.out.print("Nhập ngày tạo tài khoản (dd/mm/yyyy): ");
        String creationDate = scanner.nextLine();

        Account account = null;

        if (choice == 1) {
            double depositAmount;
            while (true) {
                System.out.print("Nhập số tiền gửi tiết kiệm: ");
                String depositAmountStr = scanner.nextLine();
                if (isPositiveDouble(depositAmountStr)) {
                    depositAmount = Double.parseDouble(depositAmountStr);
                    break;
                } else {
                    System.out.println("Số tiền gửi tiết kiệm phải là số dương. Vui lòng nhập lại.");
                }
            }

            System.out.print("Nhập ngày gửi (dd/mm/yyyy): ");
            String depositDate = scanner.nextLine();
            System.out.print("Nhập lãi suất: ");
            double interestRate = scanner.nextDouble();
            System.out.print("Nhập kỳ hạn (tháng): ");
            int term = scanner.nextInt();
            scanner.nextLine();
            account = new SavingAccount(idCounter++, accountCode, accountHolderName, creationDate,
                    depositAmount, depositDate, interestRate, term);
        } else if (choice == 2) {
            String cardNumber;
            while (true) {
                System.out.print("Nhập số thẻ (12 số bắt đầu bằng 0): ");
                cardNumber = scanner.nextLine();
                if (isValidCardNumber(cardNumber)) {
                    break;
                } else {
                    System.out.println("Số thẻ không hợp lệ hoặc đã được sử dụng. Vui lòng nhập lại.");
                }
            }

            double accountBalance;
            while (true) {
                System.out.print("Nhập số tiền trong tài khoản: ");
                String accountBalanceStr = scanner.nextLine();
                if (isPositiveDouble(accountBalanceStr)) {
                    accountBalance = Double.parseDouble(accountBalanceStr);
                    break;
                } else {
                    System.out.println("Số tiền trong tài khoản phải là số dương. Vui lòng nhập lại.");
                }
            }

            account = new CheckingAccount(idCounter++, accountCode, accountHolderName, creationDate,
                    cardNumber, accountBalance);
        }

        if (account != null) {
            accounts.add(account);
            saveAccounts();
            System.out.println("Tài khoản đã được thêm thành công.");
        }
    }


    public void deleteAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập ID tài khoản cần xóa: ");
        int id = scanner.nextInt();

        Account accountToDelete = null;
        for (Account account : accounts) {
            if (account.getId() == id) {
                accountToDelete = account;
                break;
            }
        }

        if (accountToDelete != null) {
            System.out.println("Bạn có chắc chắn muốn xóa tài khoản với ID " + id + " không? (y/n)");
            Scanner confirmationScanner = new Scanner(System.in);
            String confirmation = confirmationScanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                accounts.remove(accountToDelete);
                saveAccounts();
                System.out.println("Tài khoản đã được xóa thành công.");
            } else if (confirmation.equalsIgnoreCase("no")) {
                System.out.println("Xóa tài khoản bị hủy bỏ.");
            } else {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' hoặc 'n'.");
            }
        } else {
            System.out.println("Không tìm thấy tài khoản với ID: " + id);
        }
    }

    public void viewAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("Danh sách tài khoản rỗng.");
            return;
        }

        System.out.println("Danh sách tài khoản ngân hàng:");
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    public void searchByAccountCode() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã tài khoản cần tìm: ");
        String accountCode = scanner.nextLine();

        boolean found = false;
        for (Account account : accounts) {
            if (account.getAccountCode().equalsIgnoreCase(accountCode)) {
                System.out.println("Tài khoản tìm thấy: " + account);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy tài khoản với mã: " + accountCode);
        }
    }
}
