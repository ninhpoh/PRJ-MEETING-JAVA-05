package service;

import dao.RoomDAO;
import model.Room;

import java.util.List;

public class RoomService {
    private RoomDAO roomDAO = new RoomDAO();

    public boolean addRoom(Room room) {
        return roomDAO.insert(room);
    }

    public List<Room> getAllRooms() {
        return roomDAO.getAllRooms();
    }

    public Room getRoomById(int id) {
        return roomDAO.getRoomById(id);
    }

    public boolean updateRoom(Room room) {
        return roomDAO.update(room);
    }

    public boolean deleteRoom(int id) {
        return roomDAO.delete(id);
    }

    public List<Room> searchRoomsByName(String keyword) {
        return roomDAO.searchRoomsByName(keyword);
    }
}