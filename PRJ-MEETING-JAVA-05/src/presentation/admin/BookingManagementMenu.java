package presentation.admin;

import model.Booking;
import model.User;
import service.BookingService;
import service.UserService;
import util.ColorUtil;

import java.util.Scanner;

public class BookingManagementMenu {
    private Scanner sc = new Scanner(System.in);
    private BookingService bookingService = new BookingService();

    public void show() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                 QUẢN LÝ BOOKING                    ║");
            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Xem tất cả booking                              ║");
            System.out.println("║ 2. Duyệt booking                                   ║");
            System.out.println("║ 3. Hủy booking                                     ║");
            System.out.println("║ 4. Quay lại                                        ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showBookings();
                case 2 -> approveBooking();
                case 3 -> cancelBooking();
                case 4 -> { return; }
                default -> System.out.println(ColorUtil.RED + "Lựa chọn không hợp lệ!" + ColorUtil.RESET);
            }
        }
    }

    private void showBookings() {
        System.out.println("\n===== Danh sách Booking =====");
        System.out.println("+----+--------+--------+------------+-----------+-----------+--------------+");
        System.out.println("| ID | UserID | RoomID | Ngày       | Buổi      | Trạng thái| SupportID    |");
        System.out.println("+----+--------+--------+------------+-----------+-----------+--------------+");

        for (Booking b : bookingService.getAllBookings()) {
            if (!"CANCELLED".equalsIgnoreCase(b.getStatus())) {
                System.out.printf("| %-2d | %-6d | %-6d | %-10s | %-9s | %-9s | %-12s |%n",
                        b.getBookingId(),
                        b.getUserId(),
                        b.getRoomId(),
                        b.getBookingDate(),
                        b.getSession(),
                        b.getStatus(),
                        (b.getSupportStaffId() == 0 ? "N/A" : b.getSupportStaffId())
                );
            }
        }

        System.out.println("+----+--------+--------+------------+-----------+-----------+--------------+");
    }

    private void approveBooking() {
        System.out.print("Nhập Booking ID cần duyệt: ");
        int approveId = sc.nextInt();
        sc.nextLine();

        Booking booking = bookingService.getBookingById(approveId);

        if (booking == null) {
            System.out.println(ColorUtil.RED + "Không tìm thấy booking!" + ColorUtil.RESET);
            return;
        }

        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            System.out.println(ColorUtil.RED + "Booking này đã bị hủy, không thể duyệt!" + ColorUtil.RESET);
            return;
        }

        if (!"PENDING".equalsIgnoreCase(booking.getStatus())) {
            System.out.println(ColorUtil.YELLOW + "Chỉ có thể duyệt booking đang ở trạng thái PENDING!" + ColorUtil.RESET);
            return;
        }

        UserService userService = new UserService();
        var supports = userService.getSupportStaff();

        if (supports.isEmpty()) {
            System.out.println(ColorUtil.RED + "Không có nhân viên support khả dụng!" + ColorUtil.RESET);
            return;
        }

        System.out.println("\n===== Danh sách Support Staff khả dụng =====");
        System.out.println("+----+----------------------+--------------------------+");
        System.out.println("| ID | Tên                 | Email                     |");
        System.out.println("+----+----------------------+--------------------------+");

        for (User u : supports) {
            System.out.printf("| %-2d | %-20s | %-24s |%n",
                    u.getUserId(),
                    u.getFullName(),
                    u.getEmail());
        }

        System.out.println("+----+----------------------+--------------------------+");

        System.out.print("Chọn Support Staff ID: ");
        int supportId = sc.nextInt();
        sc.nextLine();

        boolean approved = bookingService.approveBooking(approveId, supportId);
        System.out.println(approved
                ? ColorUtil.GREEN + "Duyệt thành công với Support Staff ID " + supportId + ColorUtil.RESET
                : ColorUtil.RED + "Duyệt thất bại!" + ColorUtil.RESET);
    }

    private void cancelBooking() {
        System.out.print("Nhập Booking ID cần hủy: ");
        int cancelId = sc.nextInt(); sc.nextLine();
        Booking booking = bookingService.getBookingById(cancelId);

        if (booking == null) {
            System.out.println(ColorUtil.RED + "Không tìm thấy booking!" + ColorUtil.RESET);
            return;
        }

        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            System.out.println(ColorUtil.YELLOW + "Booking này đã bị hủy trước đó!" + ColorUtil.RESET);
            return;
        }

        boolean cancelled = bookingService.cancelBooking(cancelId);
        System.out.println(cancelled
                ? ColorUtil.GREEN + "Hủy thành công!" + ColorUtil.RESET
                : ColorUtil.RED + "Hủy thất bại!" + ColorUtil.RESET);
    }
}