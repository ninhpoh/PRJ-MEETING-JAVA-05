package presentation.support;

import model.Booking;
import service.BookingService;
import util.ColorUtil;

import java.util.List;
import java.util.Scanner;

public class SupportMenu {
    private Scanner sc = new Scanner(System.in);
    private BookingService bookingService = new BookingService();
    private int currentUserId;

    public SupportMenu(int userId) {
        this.currentUserId = userId;
    }

    public void show() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                 MENU SUPPORT STAFF                 ║");
            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Xem booking được phân công                      ║");
            System.out.println("║ 2. Thoát                                           ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showAssignedBookings();
                case 2 -> { return; }
                default -> System.out.println(ColorUtil.RED + "Lựa chọn không hợp lệ!" + ColorUtil.RESET);
            }
        }
    }

    private void showAssignedBookings() {
        System.out.println("\n===== Booking được phân công cho bạn =====");
        List<Booking> bookings = bookingService.getAllBookings();
        boolean found = false;

        System.out.println("+----+--------+--------+------------+-----------+-----------+");
        System.out.println("| ID | UserID | RoomID | Ngày       | Buổi      | Trạng thái|");
        System.out.println("+----+--------+--------+------------+-----------+-----------+");

        for (Booking b : bookings) {
            if (b.getSupportStaffId() == currentUserId) {
                System.out.printf("| %-2d | %-6d | %-6d | %-10s | %-9s | %-9s |%n",
                        b.getBookingId(),
                        b.getUserId(),
                        b.getRoomId(),
                        b.getBookingDate(),
                        b.getSession(),
                        b.getStatus());
                found = true;
            }
        }

        System.out.println("+----+--------+--------+------------+-----------+-----------+");

        if (!found) {
            System.out.println(ColorUtil.YELLOW + "Hiện chưa có booking nào được phân công cho bạn." + ColorUtil.RESET);
        }
    }
}