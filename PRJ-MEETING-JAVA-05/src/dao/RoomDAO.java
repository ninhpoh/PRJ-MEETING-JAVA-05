package dao;

import model.Room;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public boolean insert(Room room) {
        String sql = "INSERT INTO rooms (room_name, capacity, location, room_type, base_price, status) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getRoomName());
            ps.setInt(2, room.getCapacity());
            ps.setString(3, room.getLocation());
            ps.setString(4, room.getRoomType());
            ps.setBigDecimal(5, room.getBasePrice());
            ps.setString(6, room.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Room r = new Room();
                r.setRoomId(rs.getInt("room_id"));
                r.setRoomName(rs.getString("room_name"));
                r.setCapacity(rs.getInt("capacity"));
                r.setLocation(rs.getString("location"));
                r.setRoomType(rs.getString("room_type"));
                r.setBasePrice(rs.getBigDecimal("base_price"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Room getRoomById(int id) {
        String sql = "SELECT * FROM rooms WHERE room_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Room r = new Room();
                r.setRoomId(rs.getInt("room_id"));
                r.setRoomName(rs.getString("room_name"));
                r.setCapacity(rs.getInt("capacity"));
                r.setLocation(rs.getString("location"));
                r.setRoomType(rs.getString("room_type"));
                r.setBasePrice(rs.getBigDecimal("base_price"));
                r.setStatus(rs.getString("status"));
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Room room) {
        String sql = "UPDATE rooms SET room_name=?, capacity=?, location=?, room_type=?, base_price=?, status=? WHERE room_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getRoomName());
            ps.setInt(2, room.getCapacity());
            ps.setString(3, room.getLocation());
            ps.setString(4, room.getRoomType());
            ps.setBigDecimal(5, room.getBasePrice());
            ps.setString(6, room.getStatus());
            ps.setInt(7, room.getRoomId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM rooms WHERE room_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Room> searchRoomsByName(String keyword) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE room_name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room r = new Room();
                r.setRoomId(rs.getInt("room_id"));
                r.setRoomName(rs.getString("room_name"));
                r.setCapacity(rs.getInt("capacity"));
                r.setLocation(rs.getString("location"));
                r.setRoomType(rs.getString("room_type"));
                r.setBasePrice(rs.getBigDecimal("base_price"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}