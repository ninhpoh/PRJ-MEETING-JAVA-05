package service;

import dao.EquipmentDAO;
import model.Equipment;
import java.util.List;

public class EquipmentService {
    private EquipmentDAO equipmentDAO = new EquipmentDAO();

    public List<Equipment> getAllEquipment() {
        return equipmentDAO.getAllEquipment();
    }

    public boolean addEquipment(Equipment equipment) {
        return equipmentDAO.insert(equipment);
    }

    public Equipment getEquipmentById(int id) {
        return equipmentDAO.getEquipmentById(id);
    }

    public boolean updateEquipment(Equipment equipment) {
        return equipmentDAO.update(equipment);
    }

    public boolean deleteEquipment(int equipmentId) {
        return equipmentDAO.delete(equipmentId);
    }
}