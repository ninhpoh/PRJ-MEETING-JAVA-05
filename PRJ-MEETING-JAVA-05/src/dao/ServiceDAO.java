package dao;

import model.Service;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {

    public List<Service> findAll() {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT service_id, service_name, unit_price FROM services";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Service s = new Service();
                s.setServiceId(rs.getInt("service_id"));
                s.setServiceName(rs.getString("service_name"));
                s.setUnitPrice(rs.getBigDecimal("unit_price"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Service findById(int id) {
        String sql = "SELECT service_id, service_name, unit_price FROM services WHERE service_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Service s = new Service();
                s.setServiceId(rs.getInt("service_id"));
                s.setServiceName(rs.getString("service_name"));
                s.setUnitPrice(rs.getBigDecimal("unit_price"));
                return s;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Service service) {
        String sql = "INSERT INTO services(service_name, unit_price) VALUES(?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, service.getServiceName());
            ps.setBigDecimal(2, service.getUnitPrice());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Service service) {
        String sql = "UPDATE services SET service_name=?, unit_price=? WHERE service_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, service.getServiceName());
            ps.setBigDecimal(2, service.getUnitPrice());
            ps.setInt(3, service.getServiceId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM services WHERE service_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}