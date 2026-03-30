package presentation;

import model.Equipment;
import service.EquipmentService;

import java.util.List;
import java.util.Scanner;

public class EquipmentManagementMenu {
    private Scanner sc = new Scanner(System.in);
    private EquipmentService equipmentService = new EquipmentService();

    public void show() {
        while (true) {
            System.out.println("\n===== Quản lý thiết bị =====");
            System.out.println("1. Thêm thiết bị mới");
            System.out.println("2. Xem danh sách thiết bị");
            System.out.println("3. Cập nhật thông tin thiết bị");
            System.out.println("4. Xóa thiết bị");
            System.out.println("5. Quay lại Admin Menu");
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
        System.out.print("Tên thiết bị: ");
        eq.setEquipmentName(sc.nextLine());
        System.out.print("Tổng số lượng: ");
        eq.setTotalQuantity(sc.nextInt()); sc.nextLine();
        eq.setAvailableQuantity(eq.getTotalQuantity());

        System.out.println("Chọn trạng thái: 1.AVAILABLE  2.BROKEN  3.IN_USE");
        int statusChoice = sc.nextInt(); sc.nextLine();
        switch (statusChoice) {
            case 1 -> eq.setStatus("AVAILABLE");
            case 2 -> eq.setStatus("BROKEN");
            case 3 -> eq.setStatus("IN_USE");
            default -> eq.setStatus("AVAILABLE");
        }

        System.out.print("Đơn giá: ");
        double price = sc.nextDouble(); sc.nextLine();
        eq.setUnitPrice(java.math.BigDecimal.valueOf(price));

        boolean success = equipmentService.addEquipment(eq);
        System.out.println(success ? "Thêm thiết bị thành công!" : "Thêm thiết bị thất bại!");
    }

    private void updateEquipment() {
        System.out.print("Nhập Equipment ID cần cập nhật: ");
        int id = sc.nextInt(); sc.nextLine();
        Equipment eq = equipmentService.getEquipmentById(id);
        if (eq == null) {
            System.err.println("Không tìm thấy thiết bị!");
            return;
        }

        System.out.print("Tên thiết bị mới (bỏ trống nếu giữ nguyên): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) eq.setEquipmentName(name);

        System.out.print("Tổng số lượng mới (0 nếu giữ nguyên): ");
        int total = sc.nextInt(); sc.nextLine();
        if (total > 0) {
            eq.setTotalQuantity(total);
            if (eq.getAvailableQuantity() > total) {
                eq.setAvailableQuantity(total);
            }
        }

        System.out.println("Chọn trạng thái mới (0 giữ nguyên): 1.AVAILABLE  2.BROKEN  3.IN_USE");
        int statusChoice = sc.nextInt(); sc.nextLine();
        if (statusChoice == 1) eq.setStatus("AVAILABLE");
        else if (statusChoice == 2) eq.setStatus("BROKEN");
        else if (statusChoice == 3) eq.setStatus("IN_USE");

        System.out.print("Đơn giá mới (0 nếu giữ nguyên): ");
        double price = sc.nextDouble(); sc.nextLine();
        if (price > 0) eq.setUnitPrice(java.math.BigDecimal.valueOf(price));

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