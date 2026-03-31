package presentation;

import model.User;
import presentation.admin.AdminMenu;
import presentation.employee.EmployeeMenu;
import service.UserService;
import util.ColorUtil;

import java.util.Scanner;

public class AuthMenu {
    private Scanner sc = new Scanner(System.in);
    private UserService userService = new UserService();

    public void show() {
        while (true) {
            System.out.println("\n╔═════════════════════════════════════════════════════╗");
            System.out.println("║              BOOKING ROOM SYSTEM - MENU             ║");
            System.out.println("╠═════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Đăng nhập                                        ║");
            System.out.println("║ 2. Đăng ký                                          ║");
            System.out.println("║ 3. Thoát                                            ║");
            System.out.println("╚═════════════════════════════════════════════════════╝");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> {
                    System.out.println(ColorUtil.YELLOW + "Tạm biệt!" + ColorUtil.RESET);
                    return;
                }
                default -> System.out.println(ColorUtil.RED + "Lựa chọn không hợp lệ!" + ColorUtil.RESET);
            }
        }
    }

    private void login() {
        System.out.println("===== Đăng nhập =====");
        while (true) {
            System.out.print("Tên đăng nhập: ");
            String username = sc.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println(ColorUtil.RED + "Tên đăng nhập không được để trống!" + ColorUtil.RESET);
                continue;
            }

            System.out.print("Mật khẩu: ");
            String password = sc.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println(ColorUtil.RED + "Mật khẩu không được để trống!" + ColorUtil.RESET);
                continue;
            }

            User user = userService.loginUser(username, password);
            if (user != null) {
                System.out.println(ColorUtil.GREEN + "Đăng nhập thành công! Xin chào "
                        + user.getFullName() + " (" + user.getRole() + ")" + ColorUtil.RESET);
                switch (user.getRole()) {
                    case "EMPLOYEE" -> new EmployeeMenu(user).show();
                    case "SUPPORT" -> {
                        // new SupportMenu(user).show();
                    }
                    case "ADMIN" -> new AdminMenu(user).show();
                }
                break;
            } else {
                System.out.println(ColorUtil.RED + "Sai tên đăng nhập hoặc mật khẩu, thử lại!" + ColorUtil.RESET);
            }
        }
    }

    private void register() {
        System.out.println("===== Đăng ký =====");
        User user = new User();

        System.out.print("Tên đăng nhập: ");
        String username = sc.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println(ColorUtil.RED + "Tên đăng nhập không được để trống!" + ColorUtil.RESET);
            return;
        }
        user.setUsername(username);

        System.out.print("Mật khẩu: ");
        String password = sc.nextLine().trim();
        if (password.length() < 6) {
            System.out.println(ColorUtil.RED + "Mật khẩu phải có ít nhất 6 ký tự!" + ColorUtil.RESET);
            return;
        }
        user.setPassword(password);

        System.out.print("Họ tên: ");
        String fullName = sc.nextLine().trim();
        if (fullName.isEmpty()) {
            System.out.println(ColorUtil.RED + "Họ tên không được để trống!" + ColorUtil.RESET);
            return;
        }
        user.setFullName(fullName);

        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        if (!email.contains("@")) {
            System.out.println(ColorUtil.RED + "Email không hợp lệ!" + ColorUtil.RESET);
            return;
        }
        user.setEmail(email);

        System.out.print("Số điện thoại: ");
        String phone = sc.nextLine().trim();
        if (!phone.matches("\\d{9,11}")) {
            System.out.println(ColorUtil.RED + "Số điện thoại phải là 9-11 chữ số!" + ColorUtil.RESET);
            return;
        }
        user.setPhone(phone);

        System.out.print("Phòng ban: ");
        String department = sc.nextLine().trim();
        if (department.isEmpty()) {
            department = "Chưa cập nhật";
        }
        user.setDepartment(department);

        int roleChoice = -1;
        while (true) {
            System.out.println("Chọn vai trò:");
            System.out.println("1. EMPLOYEE");
            System.out.println("2. SUPPORT");
            System.out.print("Lựa chọn (1-2): ");
            try {
                roleChoice = Integer.parseInt(sc.nextLine().trim());
                if (roleChoice == 1 || roleChoice == 2) break;
                else System.out.println(ColorUtil.RED + "Chỉ được chọn 1 hoặc 2!" + ColorUtil.RESET);
            } catch (NumberFormatException e) {
                System.out.println(ColorUtil.RED + "Vui lòng nhập số nguyên!" + ColorUtil.RESET);
            }
        }
        if (roleChoice == 1) user.setRole("EMPLOYEE");
        else if (roleChoice == 2) user.setRole("SUPPORT");

        if (userService.registerUser(user)) {
            System.out.println(ColorUtil.GREEN + "Đăng ký thành công!" + ColorUtil.RESET);
        } else {
            System.out.println(ColorUtil.RED + "Đăng ký thất bại!" + ColorUtil.RESET);
        }
    }
}