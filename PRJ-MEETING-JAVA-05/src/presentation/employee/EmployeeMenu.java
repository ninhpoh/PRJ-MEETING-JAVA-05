package presentation.employee;

import model.Booking;
import model.User;
import service.BookingService;
import service.UserService;
import java.util.Scanner;

public class EmployeeMenu {
    private Scanner sc = new Scanner(System.in);
    private User currentUser;
    private UserService userService = new UserService();
    private BookingService bookingService = new BookingService(); // thêm dòng này

    public EmployeeMenu(User user) {
        this.currentUser = user;
    }

    public void show() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════╗");
            System.out.println("║                     EMPLOYEE MENU                    ║");
            System.out.println("╠══════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Booking                                           ║");
            System.out.println("║ 2. Xem booking của tôi                               ║");
            System.out.println("║ 3. Xem thông tin cá nhân                             ║");
            System.out.println("║ 4. Cập nhật thông tin cá nhân                        ║");
            System.out.println("║ 5. Đăng xuất                                         ║");
            System.out.println("╚══════════════════════════════════════════════════════╝");
            System.out.print("Chọn chức năng (1-5): ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    new BookingMenu(currentUser.getUserId()).show();
                    break;
                case 2:
                    showMyBookings();
                    break;
                case 3:
                    showPersonalInfo();
                    break;
                case 4:
                    updatePersonalInfo();
                    break;
                case 5:
                    System.out.println("Đăng xuất...");
                    return;
                default:
                    System.err.println(" Lựa chọn không hợp lệ, vui lòng thử lại!");
            }
        }
    }

    private void showPersonalInfo() {
        System.out.println("===== Thông tin cá nhân =====");
        System.out.println("ID: " + currentUser.getUserId());
        System.out.println("Tên đăng nhập: " + currentUser.getUsername());
        System.out.println("Họ tên: " + currentUser.getFullName());
        System.out.println("Email: " + (currentUser.getEmail() == null ? "Chưa cập nhật" : currentUser.getEmail()));
        System.out.println("Số điện thoại: " + (currentUser.getPhone() == null ? "Chưa cập nhật" : currentUser.getPhone()));
        System.out.println("Phòng ban: " + (currentUser.getDepartment() == null ? "Chưa cập nhật" : currentUser.getDepartment()));
        System.out.println("Vai trò: " + currentUser.getRole());
    }

    private void updatePersonalInfo() {
        System.out.println("===== Cập nhật thông tin cá nhân =====");

        System.out.print("Họ tên mới (bỏ trống nếu giữ nguyên): ");
        String fullName = sc.nextLine().trim();
        if (!fullName.isEmpty()) currentUser.setFullName(fullName);

        System.out.print("Email mới (bỏ trống nếu giữ nguyên): ");
        String email = sc.nextLine().trim();
        if (!email.isEmpty()) currentUser.setEmail(email);

        System.out.print("Số điện thoại mới (bỏ trống nếu giữ nguyên): ");
        String phone = sc.nextLine().trim();
        if (!phone.isEmpty()) currentUser.setPhone(phone);

        System.out.print("Phòng ban mới (bỏ trống nếu giữ nguyên): ");
        String dept = sc.nextLine().trim();
        if (!dept.isEmpty()) currentUser.setDepartment(dept);

        boolean success = userService.updateUser(currentUser);
        if (success) {
            System.out.println("Cập nhật thông tin thành công!");
        } else {
            System.err.println("Cập nhật thất bại!");
        }
    }

    private void showMyBookings() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                          DANH SÁCH BOOKING CỦA TÔI                         ║");
        System.out.println("╠════╦════════╦════════════════╦════════════╦════════════════════════════════╣");
        System.out.println("║ ID ║ RoomID ║ Ngày           ║ Buổi       ║ Trạng thái                     ║");
        System.out.println("╠════╬════════╬════════════════╬════════════╬════════════════════════════════╣");

        boolean hasBooking = false;
        for (Booking b : bookingService.getAllBookings()) {
            if (b.getUserId() == currentUser.getUserId()) {
                hasBooking = true;
                System.out.printf("║ %-2d ║ %-6d ║ %-14s ║ %-10s ║ %-30s ║%n",
                        b.getBookingId(),
                        b.getRoomId(),
                        b.getBookingDate(),
                        b.getSession(),
                        b.getStatus());
            }
        }

        if (!hasBooking) {
            System.out.println("║ Không có booking nào.                                                     ║");
        }

        System.out.println("╚════╩════════╩════════════════╩════════════╩════════════════════════════════╝");
    }
}