package service;

import dao.UserDAO;
import model.User;
import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public boolean registerUser(User user) {
        return userDAO.register(user);
    }

    public User loginUser(String username, String password) {
        return userDAO.login(username, password);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean updateUser(User user) {
        return userDAO.update(user);
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }
    public boolean approveUser(int userId) {
        return userDAO.approveUser(userId);
    }

    public boolean deactivateUser(int userId) {
        return userDAO.deactivateUser(userId);
    }
    public List<User> getSupportStaff() {
        return userDAO.getSupportStaff();
    }

}