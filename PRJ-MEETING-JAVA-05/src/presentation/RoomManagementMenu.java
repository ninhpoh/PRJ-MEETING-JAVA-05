package presentation;

import model.Room;
import service.RoomService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class RoomManagementMenu {
    private Scanner sc = new Scanner(System.in);
    private RoomService roomService = new RoomService();

    public void show() {
        while (true) {
            System.out.println("\n===== Quản lý phòng họp =====");
            System.out.println("1. Thêm phòng mới");
            System.out.println("2. Xem danh sách phòng");
            System.out.println("3. Cập nhật thông tin phòng");
            System.out.println("4. Xóa phòng");
            System.out.println("5. Tìm kiếm phòng theo tên");
            System.out.println("6. Quay lại Admin Menu");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addRoom();
                case 2 -> showAllRooms();
                case 3 -> updateRoom();
                case 4 -> deleteRoom();
                case 5 -> searchRoomByName();
                case 6 -> { return; }
                default -> System.err.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void addRoom() {
        Room room = new Room();
        System.out.print("Tên phòng: ");
        room.setRoomName(sc.nextLine());

        System.out.print("Sức chứa: ");
        room.setCapacity(sc.nextInt()); sc.nextLine();

        System.out.print("Vị trí: ");
        room.setLocation(sc.nextLine());

        System.out.println("Chọn loại phòng:");
        System.out.print("|1. CLASSIC");
        System.out.print("|2. VIP");
        System.out.print("|3. VIP_PRO_MAX |");
        System.out.print("Lựa chọn (1-3): ");
        int typeChoice = sc.nextInt(); sc.nextLine();

        switch (typeChoice) {
            case 1 -> room.setRoomType("CLASSIC");
            case 2 -> room.setRoomType("VIP");
            case 3 -> room.setRoomType("VIP_PRO_MAX");
            default -> {
                System.err.println("Lựa chọn không hợp lệ, mặc định CLASSIC");
                room.setRoomType("CLASSIC");
            }
        }

        System.out.print("Giá cơ bản: ");
        double price = sc.nextDouble(); sc.nextLine();
        room.setBasePrice(java.math.BigDecimal.valueOf(price));

        boolean success = roomService.addRoom(room);
        System.out.println(success ? "Thêm phòng thành công!" : "Thêm phòng thất bại!");
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

    private void updateRoom() {
        System.out.print("Nhập Room ID cần cập nhật: ");
        int id = sc.nextInt(); sc.nextLine();
        Room room = roomService.getRoomById(id);
        if (room == null) {
            System.err.println("Không tìm thấy phòng!");
            return;
        }

        System.out.print("Tên phòng mới (bỏ trống nếu giữ nguyên): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) room.setRoomName(name);

        System.out.print("Sức chứa mới (0 nếu giữ nguyên): ");
        int capacity = sc.nextInt(); sc.nextLine();
        if (capacity > 0) room.setCapacity(capacity);

        System.out.print("Vị trí mới (bỏ trống nếu giữ nguyên): ");
        String location = sc.nextLine();
        if (!location.isEmpty()) room.setLocation(location);

        System.out.println("Chọn loại phòng mới (0 nếu giữ nguyên):");
        System.out.println("1. CLASSIC");
        System.out.println("2. VIP");
        System.out.println("3. VIP_PRO_MAX");
        System.out.print("Lựa chọn (0-3): ");
        int typeChoice = sc.nextInt(); sc.nextLine();

        if (typeChoice == 1) {
            room.setRoomType("CLASSIC");
        } else if (typeChoice == 2) {
            room.setRoomType("VIP");
        } else if (typeChoice == 3) {
            room.setRoomType("VIP_PRO_MAX");
        }
        System.out.print("Giá cơ bản mới (0 nếu giữ nguyên): ");
        double price = sc.nextDouble(); sc.nextLine();
        if (price > 0) {
            room.setBasePrice(java.math.BigDecimal.valueOf(price));
        }

        boolean success = roomService.updateRoom(room);
        System.out.println(success ? "Cập nhật thành công!" : "Cập nhật thất bại!");
    }
    private void deleteRoom() {
        System.out.print("Nhập Room ID cần xóa: ");
        int id = sc.nextInt(); sc.nextLine();
        boolean success = roomService.deleteRoom(id);
        System.out.println(success ? "Xóa phòng thành công!" : "Xóa phòng thất bại!");
    }

    private void searchRoomByName() {
        System.out.print("Nhập tên phòng cần tìm (tìm kiếm tương đối): ");
        String keyword = sc.nextLine();
        List<Room> rooms = roomService.searchRoomsByName(keyword);

        if (rooms.isEmpty()) {
            System.out.println("Không tìm thấy phòng nào phù hợp!");
            return;
        }

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
}