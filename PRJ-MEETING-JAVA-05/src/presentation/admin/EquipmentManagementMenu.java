package presentation.admin;

import model.Equipment;
import service.EquipmentService;

import java.util.List;
import java.util.Scanner;

public class EquipmentManagementMenu {
    private Scanner sc = new Scanner(System.in);
    private EquipmentService equipmentService = new EquipmentService();

    public void show() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║               QUẢN LÝ THIẾT BỊ                     ║");
            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Thêm thiết bị mới                               ║");
            System.out.println("║ 2. Xem danh sách thiết bị                          ║");
            System.out.println("║ 3. Cập nhật thông tin thiết bị                     ║");
            System.out.println("║ 4. Xóa thiết bị                                    ║");
            System.out.println("║ 5. Quay lại Admin Menu                             ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addEquipment();
                    break;
                case 2:
                    showAllEquipment();
                    break;
                case 3:
                    updateEquipment();
                    break;
                case 4:
                    deleteEquipment();
                    break;
                case 5:
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void addEquipment() {
        Equipment eq = new Equipment();

        // validate tên thiết bị
        System.out.print("Tên thiết bị: ");
        String name = sc.nextLine().trim();
        while (name.isEmpty()) {
            System.err.println("Tên thiết bị không được để trống!");
            System.out.print("Tên thiết bị: ");
            name = sc.nextLine().trim();
        }
        eq.setEquipmentName(name);

        // validate tổng số lượng
        int total = 0;
        while (true) {
            System.out.print("Tổng số lượng: ");
            try {
                total = Integer.parseInt(sc.nextLine().trim());
                if (total > 0) break;
                else System.err.println("Số lượng phải > 0!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên hợp lệ!");
            }
        }
        eq.setTotalQuantity(total);
        eq.setAvailableQuantity(total);

        // validate trạng thái
        int statusChoice = -1;
        while (true) {
            System.out.println("Chọn trạng thái: 1.AVAILABLE  2.BROKEN  3.IN_USE");
            try {
                statusChoice = Integer.parseInt(sc.nextLine().trim());
                if (statusChoice >= 1 && statusChoice <= 3) break;
                else System.err.println("Chỉ được chọn từ 1–3!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
        }
        switch (statusChoice) {
            case 1 -> eq.setStatus("AVAILABLE");
            case 2 -> eq.setStatus("BROKEN");
            case 3 -> eq.setStatus("IN_USE");
        }

        // validate đơn giá
        double price = 0;
        while (true) {
            System.out.print("Đơn giá: ");
            try {
                price = Double.parseDouble(sc.nextLine().trim());
                if (price >= 0) break;
                else System.err.println("Đơn giá phải >= 0!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số thực hợp lệ!");
            }
        }
        eq.setUnitPrice(java.math.BigDecimal.valueOf(price));

        boolean success = equipmentService.addEquipment(eq);
        System.out.println(success ? "Thêm thiết bị thành công!" : "Thêm thiết bị thất bại!");
    }

    private void updateEquipment() {
        int id = -1;
        while (true) {
            System.out.print("Nhập Equipment ID cần cập nhật: ");
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên hợp lệ!");
            }
        }

        Equipment eq = equipmentService.getEquipmentById(id);
        if (eq == null) {
            System.err.println("Không tìm thấy thiết bị!");
            return;
        }

        System.out.print("Tên thiết bị mới (bỏ trống nếu giữ nguyên): ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) eq.setEquipmentName(name);

        // validate tổng số lượng
        while (true) {
            System.out.print("Tổng số lượng mới (0 nếu giữ nguyên): ");
            try {
                int total = Integer.parseInt(sc.nextLine().trim());
                if (total >= 0) {
                    if (total > 0) {
                        eq.setTotalQuantity(total);
                        if (eq.getAvailableQuantity() > total) {
                            eq.setAvailableQuantity(total);
                        }
                    }
                    break;
                } else {
                    System.err.println("Số lượng phải >= 0!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
        }

        // validate trạng thái
        int statusChoice = -1;
        while (true) {
            System.out.println("Chọn trạng thái mới (0 giữ nguyên): 1.AVAILABLE  2.BROKEN  3.IN_USE");
            try {
                statusChoice = Integer.parseInt(sc.nextLine().trim());
                if (statusChoice >= 0 && statusChoice <= 3) break;
                else System.err.println("Chỉ được chọn từ 0–3!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
        }
        if (statusChoice == 1) eq.setStatus("AVAILABLE");
        else if (statusChoice == 2) eq.setStatus("BROKEN");
        else if (statusChoice == 3) eq.setStatus("IN_USE");

        // validate đơn giá
        while (true) {
            System.out.print("Đơn giá mới (0 nếu giữ nguyên): ");
            try {
                double price = Double.parseDouble(sc.nextLine().trim());
                if (price >= 0) {
                    if (price > 0) eq.setUnitPrice(java.math.BigDecimal.valueOf(price));
                    break;
                } else {
                    System.err.println("Đơn giá phải >= 0!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số thực hợp lệ!");
            }
        }

        boolean success = equipmentService.updateEquipment(eq);
        System.out.println(success ? "Cập nhật thành công!" : "Cập nhật thất bại!");
    }

    private void deleteEquipment() {
        System.out.print("Nhập Equipment ID cần xóa: ");
        int id = sc.nextInt(); sc.nextLine();
        boolean success = equipmentService.deleteEquipment(id);
        System.out.println(success ? "Xóa thiết bị thành công!" : "Xóa thiết bị thất bại!");
    }
    private void showAllEquipment() {
        List<Equipment> list = equipmentService.getAllEquipment();
        System.out.println("\n+----+----------------+--------------+----------------+-----------+-------------+");
        System.out.println("| ID | Tên thiết bị   | Tổng số lượng| Số lượng còn   | Trạng thái| Đơn giá     |");
        System.out.println("+----+----------------+--------------+----------------+-----------+-------------+");
        for (Equipment e : list) {
            System.out.printf("| %-2d | %-14s | %-12d | %-14d | %-9s | %-11.2f |%n",
                    e.getEquipmentId(), e.getEquipmentName(),
                    e.getTotalQuantity(), e.getAvailableQuantity(),
                    e.getStatus(), e.getUnitPrice());
        }
        System.out.println("+----+----------------+--------------+----------------+-----------+-------------+");
    }
}