package presentation.admin;

import model.Service;
import service.ServiceService;
import java.util.List;
import java.util.Scanner;

public class ServiceManagementMenu {
    private Scanner sc = new Scanner(System.in);
    private ServiceService serviceService = new ServiceService();

    public void show() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                 QUẢN LÝ DỊCH VỤ                    ║");
            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Xem danh sách dịch vụ                           ║");
            System.out.println("║ 2. Thêm dịch vụ                                    ║");
            System.out.println("║ 3. Cập nhật dịch vụ                                ║");
            System.out.println("║ 4. Xóa dịch vụ                                     ║");
            System.out.println("║ 5. Quay lại Admin Menu                             ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.print("Chọn: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1 -> showAllServices();
                case 2 -> addService();
                case 3 -> updateService();
                case 4 -> deleteService();
                case 5 -> { return; }
                default -> System.err.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void showAllServices() {
        List<Service> services = serviceService.getAllServices();
      System.out.println("\n╔═════════════════════════════════════════════════════╗");
        System.out.println("║                 DANH SÁCH DỊCH VỤ                   ║");
        System.out.println("╠════╦════════════════════╦═══════════════════════════╣");
        System.out.println("║ ID ║ Tên dịch vụ        ║ Giá                       ║");
        System.out.println("╠════╬════════════════════╬═══════════════════════════╣");
        for (Service s : services) {
            System.out.printf("║ %-2d ║ %-18s ║ %-25s ║%n",
                    s.getServiceId(),
                    s.getServiceName(),
                    s.getUnitPrice());
        }
        System.out.println("╚════╩════════════════════╩═══════════════════════════╝");
    }

    private void addService() {
        Service service = new Service();

        System.out.print("Tên dịch vụ: ");
        String name = sc.nextLine().trim();
        while (name.isEmpty()) {
            System.err.println("Tên dịch vụ không được để trống!");
            System.out.print("Tên dịch vụ: ");
            name = sc.nextLine().trim();
        }
        service.setServiceName(name);

        double price = 0;
        while (true) {
            System.out.print("Giá dịch vụ: ");
            try {
                price = Double.parseDouble(sc.nextLine().trim());
                if (price >= 0) break;
                else System.err.println("Giá phải >= 0!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số thực hợp lệ!");
            }
        }
        service.setUnitPrice(java.math.BigDecimal.valueOf(price));

        boolean success = serviceService.addService(service);
        System.out.println(success ? "Thêm dịch vụ thành công!" : "Thêm dịch vụ thất bại!");
    }

    private void updateService() {
        int id = -1;
        while (true) {
            System.out.print("Nhập ID dịch vụ cần cập nhật: ");
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên hợp lệ!");
            }
        }

        Service service = serviceService.getServiceById(id);
        if (service == null) {
            System.err.println("Không tìm thấy dịch vụ!");
            return;
        }

        System.out.print("Tên dịch vụ mới (bỏ trống nếu giữ nguyên): ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) service.setServiceName(name);

        System.out.print("Giá dịch vụ mới (0 nếu giữ nguyên): ");
        try {
            double price = Double.parseDouble(sc.nextLine().trim());
            if (price > 0) service.setUnitPrice(java.math.BigDecimal.valueOf(price));
        } catch (NumberFormatException e) {
            System.err.println("Giá không hợp lệ, giữ nguyên!");
        }

        boolean success = serviceService.updateService(service);
        System.out.println(success ? "Cập nhật thành công!" : "Cập nhật thất bại!");
    }

    private void deleteService() {
        int id = -1;
        while (true) {
            System.out.print("Nhập ID dịch vụ cần xóa: ");
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên hợp lệ!");
            }
        }

        boolean success = serviceService.deleteService(id);
        System.out.println(success ? "Xóa thành công!" : "Xóa thất bại!");
    }
}