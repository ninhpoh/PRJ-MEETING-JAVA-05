package dao;

import model.Equipment;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {
    public List<Equipment> getAllEquipment() {
        List<Equipment> list = new ArrayList<>();
        String sql = "SELECT * FROM equipment";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Equipment e = new Equipment();
                e.setEquipmentId(rs.getInt("equipment_id"));
                e.setEquipmentName(rs.getString("equipment_name"));
                e.setTotalQuantity(rs.getInt("total_quantity"));
                e.setAvailableQuantity(rs.getInt("available_quantity"));
                e.setStatus(rs.getString("status"));
                e.setUnitPrice(rs.getBigDecimal("unit_price"));
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public boolean insert(Equipment equipment) {
        String sql = "INSERT INTO equipment(equipment_name, total_quantity, available_quantity, status, unit_price) VALUES(?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, equipment.getEquipmentName());
            ps.setInt(2, equipment.getTotalQuantity());
            ps.setInt(3, equipment.getAvailableQuantity());
            ps.setString(4, equipment.getStatus());
            ps.setBigDecimal(5, equipment.getUnitPrice());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean update(Equipment equipment) {
        String sql = "UPDATE equipment SET equipment_name=?, total_quantity=?, available_quantity=?, status=?, unit_price=? WHERE equipment_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, equipment.getEquipmentName());
            ps.setInt(2, equipment.getTotalQuantity());
            ps.setInt(3, equipment.getAvailableQuantity());
            ps.setString(4, equipment.getStatus());
            ps.setBigDecimal(5, equipment.getUnitPrice());
            ps.setInt(6, equipment.getEquipmentId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(int equipmentId) {
        String sql = "DELETE FROM equipment WHERE equipment_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, equipmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Equipment getEquipmentById(int id) {
        String sql = "SELECT * FROM equipment WHERE equipment_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Equipment e = new Equipment();
                e.setEquipmentId(rs.getInt("equipment_id"));
                e.setEquipmentName(rs.getString("equipment_name"));
                e.setTotalQuantity(rs.getInt("total_quantity"));
                e.setAvailableQuantity(rs.getInt("available_quantity"));
                e.setStatus(rs.getString("status"));
                e.setUnitPrice(rs.getBigDecimal("unit_price"));
                return e;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;

    }
}