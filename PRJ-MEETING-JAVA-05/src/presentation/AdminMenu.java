package presentation;

import model.User;
import service.RoomService;
import service.EquipmentService;
import service.BookingService;
import model.Booking;
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
            System.out.println("\n======================================");
            System.out.println("            ADMIN DASHBOARD           ");
            System.out.println("======================================");
            System.out.println("1. Quản lý người dùng");
            System.out.println("2. Quản lý phòng họp");
            System.out.println("3. Quản lý thiết bị");
            System.out.println("4. Quản lý dịch vụ");
            System.out.println("5. Quản lý booking");
            System.out.println("6. Xem báo cáo");
            System.out.println("7. Đăng xuất");
            System.out.println("======================================");
            System.out.print("Chọn chức năng (1-7): ");

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
                    System.out.println(">>> Gọi ServiceService.manageServices()");
                    break;
                case 5:
                    manageBookings();
                    break;
                case 6:
                    System.out.println(">>> Gọi ReportService.generateReports()");
                    break;
                case 7:
                    System.out.println("Đăng xuất...");
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void manageBookings() {
        while (true) {
            System.out.println("\n===== Quản lý Booking =====");
            System.out.println("1. Xem tất cả booking");
            System.out.println("2. Duyệt booking");
            System.out.println("3. Hủy booking");
            System.out.println("4. Quay lại");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    for (Booking b : bookingService.getAllBookings()) {
                        System.out.printf("ID:%d | User:%d | Room:%d | Ngày:%s | Buổi:%s | Trạng thái:%s%n",
                                b.getBookingId(),
                                b.getUserId(),
                                b.getRoomId(),
                                b.getBookingDate(),
                                b.getSession(),
                                b.getStatus());
                    }
                    break;
                case 2:
                    System.out.print("Nhập Booking ID cần duyệt: ");
                    int approveId = sc.nextInt(); sc.nextLine();
                    boolean approved = bookingService.approveBooking(approveId);
                    System.out.println(approved ? "Duyệt thành công!" : "Duyệt thất bại!");
                    break;
                case 3:
                    System.out.print("Nhập Booking ID cần hủy: ");
                    int cancelId = sc.nextInt(); sc.nextLine();
                    boolean cancelBooking = bookingService.cancelBooking(cancelId);
                    System.out.println(cancelBooking ? "Hủy thành công!" : "Hủy thất bại!");
                    break;
                case 4:
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}