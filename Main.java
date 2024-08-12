import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountManager manager = new AccountManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Thêm mới tài khoản");
            System.out.println("2. Xóa xóa tài khoản");
            System.out.println("3. Xem danh sách tài khoản");
            System.out.println("4. Tìm kiếm tài khoản");
            System.out.println("5. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manager.addAccount();
                    break;
                case 2:
                    manager.deleteAccount();
                    break;
                case 3:
                    manager.viewAccounts();
                    break;
                case 4:
                    manager.searchByAccountCode();
                    break;
                case 5:
                    System.out.println("Thoát ứng dụng.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }
}
