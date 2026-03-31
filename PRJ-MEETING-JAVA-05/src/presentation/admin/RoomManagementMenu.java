package presentation.admin;

import model.Room;
import service.RoomService;
import java.util.List;
import java.util.Scanner;

public class RoomManagementMenu {
    private Scanner sc = new Scanner(System.in);
    private RoomService roomService = new RoomService();

    public void show() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║               QUẢN LÝ PHÒNG HỌP                    ║");
            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Thêm phòng mới                                  ║");
            System.out.println("║ 2. Xem danh sách phòng                             ║");
            System.out.println("║ 3. Cập nhật thông tin phòng                        ║");
            System.out.println("║ 4. Tìm kiếm phòng theo tên                         ║");
            System.out.println("║ 5. Quay lại Admin Menu                             ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addRoom();
                case 2 -> showAllRooms();
                case 3 -> updateRoom();
                case 4 -> searchRoomByName();
                case 5 -> { return; }
                default -> System.err.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void addRoom() {
        Room room = new Room();

        System.out.print("Tên phòng: ");
        String name = sc.nextLine().trim();
        while (name.isEmpty()) {
            System.err.println("Tên phòng không được để trống!");
            System.out.print("Tên phòng: ");
            name = sc.nextLine().trim();
        }
        room.setRoomName(name);

        int capacity = 0;
        while (true) {
            System.out.print("Sức chứa: ");
            try {
                capacity = Integer.parseInt(sc.nextLine().trim());
                if (capacity > 0) break;
                else System.err.println("Sức chứa phải > 0!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên hợp lệ!");
            }
        }
        room.setCapacity(capacity);

        System.out.print("Vị trí: ");
        String location = sc.nextLine().trim();
        while (location.isEmpty()) {
            System.err.println("Vị trí không được để trống!");
            System.out.print("Vị trí: ");
            location = sc.nextLine().trim();
        }
        room.setLocation(location);

        System.out.println("Chọn loại phòng:");
        System.out.println("1. CLASSIC");
        System.out.println("2. VIP");
        System.out.println("3. VIP_PRO_MAX");
        int typeChoice = 0;
        while (true) {
            try {
                System.out.print("Lựa chọn (1-3): ");
                typeChoice = Integer.parseInt(sc.nextLine().trim());
                if (typeChoice >= 1 && typeChoice <= 3) break;
                else System.err.println("Chỉ được chọn 1–3!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
        }
        switch (typeChoice) {
            case 1 -> room.setRoomType("CLASSIC");
            case 2 -> room.setRoomType("VIP");
            case 3 -> room.setRoomType("VIP_PRO_MAX");
        }

        double price = 0;
        while (true) {
            System.out.print("Giá cơ bản: ");
            try {
                price = Double.parseDouble(sc.nextLine().trim());
                if (price >= 0) break;
                else System.err.println("Giá phải >= 0!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số thực hợp lệ!");
            }
        }
        room.setBasePrice(java.math.BigDecimal.valueOf(price));

        // chọn trạng thái phòng
        System.out.println("Chọn trạng thái phòng:");
        System.out.println("1. AVAILABLE");
        System.out.println("2. UNAVAILABLE");
        System.out.println("3. MAINTENANCE");
        int statusChoice = 0;
        while (true) {
            try {
                System.out.print("Lựa chọn (1-3): ");
                statusChoice = Integer.parseInt(sc.nextLine().trim());
                if (statusChoice >= 1 && statusChoice <= 3) break;
                else System.err.println("Chỉ được chọn 1–3!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
        }
        switch (statusChoice) {
            case 1 -> room.setStatus("AVAILABLE");
            case 2 -> room.setStatus("UNAVAILABLE");
            case 3 -> room.setStatus("MAINTENANCE");
        }

        boolean success = roomService.addRoom(room);
        System.out.println(success ? "Thêm phòng thành công!" : "Thêm phòng thất bại!");
    }

    private void showAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        System.out.println("\n+----+----------------+-----------+--------------------+--------------+-------------+--------------+");
        System.out.println("| ID | Tên phòng      | Sức chứa  | Vị trí             | Loại phòng   | Giá cơ bản  | Trạng thái   |");
        System.out.println("+----+----------------+-----------+--------------------+--------------+-------------+--------------+");
        for (Room r : rooms) {
            System.out.printf("| %-2d | %-14s | %-9d | %-18s | %-12s | %-11.2f | %-12s |%n",
                    r.getRoomId(), r.getRoomName(), r.getCapacity(),
                    r.getLocation(), r.getRoomType(), r.getBasePrice(), r.getStatus());
        }
        System.out.println("+----+----------------+-----------+--------------------+--------------+-------------+--------------+");
    }

    private void updateRoom() {
        int id = -1;
        while (true) {
            System.out.print("Nhập Room ID cần cập nhật: ");
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên hợp lệ!");
            }
        }

        Room room = roomService.getRoomById(id);
        if (room == null) {
            System.err.println("Không tìm thấy phòng!");
            return;
        }

        System.out.print("Tên phòng mới (bỏ trống nếu giữ nguyên): ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) room.setRoomName(name);

        // sức chứa
        while (true) {
            System.out.print("Sức chứa mới (0 nếu giữ nguyên): ");
            try {
                int capacity = Integer.parseInt(sc.nextLine().trim());
                if (capacity >= 0) {
                    if (capacity > 0) room.setCapacity(capacity);
                    break;
                } else {
                    System.err.println("Sức chứa phải >= 0!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
        }

        System.out.print("Vị trí mới (bỏ trống nếu giữ nguyên): ");
        String location = sc.nextLine().trim();
        if (!location.isEmpty()) room.setLocation(location);

        // loại phòng
        int typeChoice = -1;
        while (true) {
            System.out.println("Chọn loại phòng mới (0 nếu giữ nguyên):");
            System.out.println("1. CLASSIC");
            System.out.println("2. VIP");
            System.out.println("3. VIP_PRO_MAX");
            System.out.print("Lựa chọn (0-3): ");
            try {
                typeChoice = Integer.parseInt(sc.nextLine().trim());
                if (typeChoice >= 0 && typeChoice <= 3) break;
                else System.err.println("Chỉ được chọn từ 0–3!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
        }
        if (typeChoice == 1) room.setRoomType("CLASSIC");
        else if (typeChoice == 2) room.setRoomType("VIP");
        else if (typeChoice == 3) room.setRoomType("VIP_PRO_MAX");

        // giá cơ bản
        while (true) {
            System.out.print("Giá cơ bản mới (0 nếu giữ nguyên): ");
            try {
                double price = Double.parseDouble(sc.nextLine().trim());
                if (price >= 0) {
                    if (price > 0) room.setBasePrice(java.math.BigDecimal.valueOf(price));
                    break;
                } else {
                    System.err.println("Giá phải >= 0!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số thực hợp lệ!");
            }
        }

        // trạng thái phòng
        int statusChoice = -1;
        while (true) {
            System.out.println("Chọn trạng thái phòng mới (0 nếu giữ nguyên):");
            System.out.println("1. AVAILABLE");
            System.out.println("2. UNAVAILABLE");
            System.out.println("3. MAINTENANCE");
            System.out.print("Lựa chọn (0-3): ");
            try {
                statusChoice = Integer.parseInt(sc.nextLine().trim());
                if (statusChoice >= 0 && statusChoice <= 3) break;
                else System.err.println("Chỉ được chọn từ 0–3!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
        }
        if (statusChoice == 1) room.setStatus("AVAILABLE");
        else if (statusChoice == 2) room.setStatus("UNAVAILABLE");
        else if (statusChoice == 3) room.setStatus("MAINTENANCE");

        boolean success = roomService.updateRoom(room);
        System.out.println(success ? "Cập nhật thành công!" : "Cập nhật thất bại!");
    }

    private void searchRoomByName() {
        System.out.print("Nhập tên phòng cần tìm (tìm kiếm tương đối): ");
        String keyword = sc.nextLine().trim();

        List<Room> rooms = roomService.searchRoomsByName(keyword);

        if (rooms.isEmpty()) {
            System.out.println("Không tìm thấy phòng nào phù hợp!");
            return;
        }

        System.out.println("\n+----+----------------+-----------+--------------------+--------------+-------------+--------------+");
        System.out.println("| ID | Tên phòng      | Sức chứa  | Vị trí             | Loại phòng   | Giá cơ bản  | Trạng thái   |");
        System.out.println("+----+----------------+-----------+--------------------+--------------+-------------+--------------+");
        for (Room r : rooms) {
            System.out.printf("| %-2d | %-14s | %-9d | %-18s | %-12s | %-11.2f | %-12s |%n",
                    r.getRoomId(), r.getRoomName(), r.getCapacity(),
                    r.getLocation(), r.getRoomType(), r.getBasePrice(), r.getStatus());
        }
        System.out.println("+----+----------------+-----------+--------------------+--------------+-------------+--------------+");
    }
}