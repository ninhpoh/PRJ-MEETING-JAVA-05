package presentation;

import service.UserService;
import model.User;
import java.util.Scanner;
import java.util.List;

public class UserManagementMenu {
    private Scanner sc = new Scanner(System.in);
    private UserService userService = new UserService();

    public void show() {
        while (true) {
            System.out.println("\n===== Quản lý người dùng =====");
            System.out.println("1. Thêm người dùng mới");
            System.out.println("2. Xem danh sách người dùng");
            System.out.println("3. Cập nhật thông tin người dùng");
            System.out.println("4. Xóa người dùng");
            System.out.println("5. Quay lại Admin Menu");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    showAllUsers();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void addUser() {
        System.out.println("===== Thêm người dùng mới =====");
        User user = new User();
        System.out.print("Tên đăng nhập: ");
        user.setUsername(sc.nextLine());
        System.out.print("Mật khẩu: ");
        user.setPassword(sc.nextLine());
        System.out.print("Họ tên: ");
        user.setFullName(sc.nextLine());
        System.out.print("Email: ");
        user.setEmail(sc.nextLine());
        System.out.print("Số điện thoại: ");
        user.setPhone(sc.nextLine());
        System.out.print("Phòng ban: ");
        user.setDepartment(sc.nextLine());
        System.out.print("Vai trò (ADMIN/EMPLOYEE/SUPPORT): ");
        user.setRole(sc.nextLine().toUpperCase());

        boolean success = userService.registerUser(user);
        if (success) {
            System.out.println("Thêm người dùng thành công!");
        } else {
            System.err.println("Thêm người dùng thất bại!");
        }
    }

    private void showAllUsers() {
        System.out.println("\n╔═════════════════════════════════════════════════════╗");
        System.out.println("║                 DANH SÁCH NGƯỜI DÙNG                ║");
        System.out.println("╠════╦════════════╦══════════════════════╦════════════╣");
        System.out.println("║ ID ║ Username   ║ Họ tên               ║ Vai trò    ║");
        System.out.println("╠════╬════════════╬══════════════════════╬════════════╣");

        List<User> users = userService.getAllUsers();
        for (User u : users) {
            System.out.printf("║ %-2d ║ %-10s ║ %-20s ║ %-10s ║%n",
                    u.getUserId(),
                    u.getUsername(),
                    u.getFullName(),
                    u.getRole());
        }

        System.out.println("╚════╩════════════╩══════════════════════╩════════════╝");
    }

    private void updateUser() {
        System.out.print("Nhập ID người dùng cần cập nhật: ");
        int id = sc.nextInt();
        sc.nextLine();

        User user = new User();
        user.setUserId(id);

        System.out.print("Họ tên mới: ");
        user.setFullName(sc.nextLine());
        System.out.print("Email mới: ");
        user.setEmail(sc.nextLine());
        System.out.print("Số điện thoại mới: ");
        user.setPhone(sc.nextLine());
        System.out.print("Phòng ban mới: ");
        user.setDepartment(sc.nextLine());

        boolean success = userService.updateUser(user);
        if (success) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.err.println("Cập nhật thất bại!");
        }
    }

    private void deleteUser() {
        System.out.print("Nhập ID người dùng cần xóa: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean success = userService.deleteUser(id);
        if (success) {
            System.out.println("Xóa thành công!");
        } else {
            System.err.println("Xóa thất bại!");
        }
        }
    }
}