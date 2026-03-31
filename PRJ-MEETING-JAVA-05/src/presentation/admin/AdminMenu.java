package presentation.admin;

import model.User;
import service.RoomService;
import service.EquipmentService;
import service.BookingService;

import java.util.Scanner;

public class AdminMenu {
    private Scanner sc = new Scanner(System.in);
    private User currentUser;
    private RoomService roomService = new RoomService();
    private EquipmentService equipmentService = new EquipmentService();
    private BookingService bookingService = new BookingService();

    public AdminMenu(User user) {
        this.currentUser = user;
    }

    public void show() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                 ADMIN DASHBOARD                    ║");
            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Quản lý người dùng                              ║");
            System.out.println("║ 2. Quản lý phòng họp                               ║");
            System.out.println("║ 3. Quản lý thiết bị                                ║");
            System.out.println("║ 4. Quản lý dịch vụ                                 ║");
            System.out.println("║ 5. Quản lý booking                                 ║");
            System.out.println("║ 6. Đăng xuất                                       ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.print("Chọn chức năng (1-6): ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    new UserManagementMenu().show();
                    break;
                case 2:
                    new RoomManagementMenu().show();
                    break;
                case 3:
                    new EquipmentManagementMenu().show();
                    break;
                case 4:
                    new ServiceManagementMenu().show();
                    break;
                case 5:
                    new BookingManagementMenu().show();
                    break;
                case 6:
                    System.out.println("Đăng xuất...");
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}