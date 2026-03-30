package presentation;

import model.Room;
import service.BookingService;
import model.Booking;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import service.RoomService;

public class BookingMenu {
    private Scanner sc = new Scanner(System.in);
    private BookingService bookingService = new BookingService();
    private RoomService roomService = new RoomService();
    private int currentUserId;

    public BookingMenu(int userId) {
        this.currentUserId = userId;
    }

    public void show() {
        while (true) {
            System.out.println("\n===== Quản lý Booking =====");
            System.out.println("1. Đặt phòng họp");
            System.out.println("2. Xem danh sách Booking");
            System.out.println("3. Hủy Booking");
            System.out.println("4. Quay lại Menu");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    createBooking();
                    break;
                case 2:
                    showAllBookings();
                    break;
                case 3:
                    cancelBooking();
                    break;
                case 4:
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void createBooking() {
        showAllRooms();

        Booking booking = new Booking();
        booking.setUserId(currentUserId);

        System.out.print("Nhập Room ID: ");
        booking.setRoomId(sc.nextInt());
        sc.nextLine();

        System.out.print("Chọn buổi (1. Sáng / 2. Chiều): ");
        int sessionChoice = sc.nextInt();
        sc.nextLine();

        if (sessionChoice == 1) {
            booking.setSession("MORNING");
        } else if (sessionChoice == 2) {
            booking.setSession("AFTERNOON");
        } else {
            System.err.println("Lựa chọn không hợp lệ!");
            return;
        }

        booking.setBookingDate(java.time.LocalDate.now());
        booking.setStatus("PENDING");

        boolean success = bookingService.createBooking(booking);
        if (success) {
            System.out.println("Đặt phòng thành công!");
        } else {
            System.err.println("Đặt phòng thất bại!");
        }
    }

    private void showAllBookings() {
        System.out.println("\n===== Danh sách Booking =====");
        for (Booking b : bookingService.getAllBookings()) {
            System.out.printf("ID: %d | User: %d | Room: %d | Ngày: %s | Buổi: %s | Trạng thái: %s%n",
                    b.getBookingId(),
                    b.getUserId(),
                    b.getRoomId(),
                    b.getBookingDate(),
                    b.getSession(),
                    b.getStatus());
        }
    }

    private void showAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        System.out.println("\n+----+----------------+-----------+--------------------+--------------+-------------+");
        System.out.println("| ID | Tên phòng      | Sức chứa  | Vị trí             | Loại phòng   | Giá cơ bản  |");
        System.out.println("+----+----------------+-----------+--------------------+--------------+-------------+");
        for (Room r : rooms) {
            System.out.printf("| %-2d | %-14s | %-9d | %-18s | %-12s | %-11.2f |%n",
                    r.getRoomId(), r.getRoomName(), r.getCapacity(),
                    r.getLocation(), r.getRoomType(), r.getBasePrice());
        }
        System.out.println("+----+----------------+-----------+--------------------+--------------+-------------+");
    }

    private void cancelBooking() {
        System.out.print("Nhập Booking ID cần hủy: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean success = bookingService.cancelBooking(id);
        if (success) {
            System.out.println("Hủy Booking thành công!");
        } else {
            System.err.println("Hủy Booking thất bại!");
        }
    }
}