package presentation.admin;

import service.UserService;
import model.User;
import util.ColorUtil;
import java.util.Scanner;
import java.util.List;

public class UserManagementMenu {
    private Scanner sc = new Scanner(System.in);
    private UserService userService = new UserService();

    public void show() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║               QUẢN LÝ NGƯỜI DÙNG                   ║");
            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Xem danh sách người dùng                        ║");
            System.out.println("║ 2. Duyệt Support                                   ║");
            System.out.println("║ 3. Vô hiệu hóa người dùng                          ║");
            System.out.println("║ 4. Quay lại Admin Menu                             ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showAllUsers();
                case 2 -> approveSupport();
                case 3 -> deactivateUser();
                case 4 -> { return; }
                default -> System.out.println(ColorUtil.RED + "Lựa chọn không hợp lệ!" + ColorUtil.RESET);
            }
        }
    }

    private void showAllUsers() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        DANH SÁCH NGƯỜI DÙNG                      ║");
        System.out.println("╠════╦════════════╦══════════════════════╦════════════╦════════════╣");
        System.out.println("║ ID ║ Username   ║ Họ tên               ║ Vai trò    ║ Trạng thái ║");
        System.out.println("╠════╬════════════╬══════════════════════╬════════════╬════════════╣");

        List<User> users = userService.getAllUsers();
        for (User u : users) {
            System.out.printf("║ %-2d ║ %-10s ║ %-20s ║ %-10s ║ %-10s ║%n",
                    u.getUserId(),
                    u.getUsername(),
                    u.getFullName(),
                    u.getRole(),
                    u.getStatus());
        }

        System.out.println("╚════╩════════════╩══════════════════════╩════════════╩════════════╝");
    }

    private void approveSupport() {
        int id = -1;
        while (true) {
            System.out.print("Nhập ID Support cần duyệt: ");
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println(ColorUtil.RED + "Vui lòng nhập số nguyên hợp lệ!" + ColorUtil.RESET);
            }
        }

        boolean approved = userService.approveUser(id);
        System.out.println(approved
                ? ColorUtil.GREEN + "Duyệt Support thành công!" + ColorUtil.RESET
                : ColorUtil.RED + "Duyệt thất bại!" + ColorUtil.RESET);
    }

    private void deactivateUser() {
        int id = -1;
        while (true) {
            System.out.print("Nhập ID người dùng cần vô hiệu hóa: ");
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println(ColorUtil.RED + "Vui lòng nhập số nguyên hợp lệ!" + ColorUtil.RESET);
            }
        }

        boolean deactivated = userService.deactivateUser(id);
        System.out.println(deactivated
                ? ColorUtil.GREEN + "Vô hiệu hóa thành công!" + ColorUtil.RESET
                : ColorUtil.RED + "Vô hiệu hóa thất bại!" + ColorUtil.RESET);
    }
}