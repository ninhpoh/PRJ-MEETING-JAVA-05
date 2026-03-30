package presentation;

import model.User;
import service.UserService;
import java.util.Scanner;

public class AuthMenu {
    private Scanner sc = new Scanner(System.in);
    private UserService userService = new UserService();

    public void show() {
        while (true) {
            System.out.println("===== Booking Room System =====");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký");
            System.out.println("3. Thoát");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Tạm biệt!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void login() {
        System.out.println("===== Đăng nhập =====");
        while (true) {
            System.out.print("Tên đăng nhập: ");
            String username = sc.nextLine().trim();
            if (username.isEmpty()) {
                System.err.println("Tên đăng nhập không được để trống");
                continue;
            }

            System.out.print("Mật khẩu: ");
            String password = sc.nextLine().trim();
            if (password.isEmpty()) {
                System.err.println("Mật khẩu không được để trống!");
                continue;
            }

            User user = userService.loginUser(username, password);
            if (user != null) {
                System.out.println("Đăng nhập thành công! Xin chào " + user.getFullName() + " (" + user.getRole() + ")");
                switch (user.getRole()) {
                    case "EMPLOYEE":
                        new EmployeeMenu(user).show();
                        break;
                    case "SUPPORT":
//                        new SupportMenu(user).show();
                        break;
                    case "ADMIN":
                        new AdminMenu(user).show();
                        break;
                }
                break;
            } else {
                System.err.println("Sai tên đăng nhập hoặc mật khẩu, thử lại!");
            }
        }
    }
    private void register() {
        System.out.println("===== Đăng ký =====");
        User user = new User();

        System.out.print("Tên đăng nhập: ");
        String username = sc.nextLine();
        if (username.isEmpty()) {
            System.out.println("Tên đăng nhập không được để trống!");
            return;
        }
        user.setUsername(username);

        System.out.print("Mật khẩu: ");
        String password = sc.nextLine();
        if (password.length() < 6) {
            System.out.println("Mật khẩu phải có ít nhất 6 ký tự!");
            return;
        }
        user.setPassword(password);

        System.out.print("Họ tên: ");
        String fullName = sc.nextLine();
        if (fullName.isEmpty()) {
            System.out.println("Họ tên không được để trống!");
            return;
        }
        user.setFullName(fullName);

        System.out.print("Email: ");
        String email = sc.nextLine();
        if (!email.contains("@")) {
            System.out.println("Email không hợp lệ!");
            return;
        }
        user.setEmail(email);

        System.out.print("Số điện thoại: ");
        String phone = sc.nextLine();
        if (!phone.matches("\\d{9,11}")) {
            System.out.println("Số điện thoại phải là 9-11 chữ số!");
            return;
        }
        user.setPhone(phone);

        System.out.print("Phòng ban: ");
        String department = sc.nextLine();
        if (department.isEmpty()) {
            department = "Chưa cập nhật";
        }
        user.setDepartment(department);

        System.out.print("Vai trò (EMPLOYEE/SUPPORT): ");
        String role = sc.nextLine().toUpperCase();
        if (!role.equals("EMPLOYEE") && !role.equals("SUPPORT")) {
            System.out.println("Vai trò chỉ được chọn EMPLOYEE hoặc SUPPORT!");
            return;
        }
        user.setRole(role);

        if (userService.registerUser(user)) {
            System.out.println("Đăng ký thành công!");
        } else {
            System.out.println("Đăng ký thất bại!");
        }
    }



}