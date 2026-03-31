package service;

import dao.ServiceDAO;
import model.Service;
import java.util.List;

public class ServiceService {
    private ServiceDAO serviceDAO = new ServiceDAO();

    public List<Service> getAllServices() { return serviceDAO.findAll(); }
    public Service getServiceById(int id) { return serviceDAO.findById(id); }
    public boolean addService(Service service) { return serviceDAO.insert(service); }
    public boolean updateService(Service service) { return serviceDAO.update(service); }
    public boolean deleteService(int id) { return serviceDAO.delete(id); }
}