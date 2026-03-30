package service;

import dao.BookingDAO;
import model.Booking;
import java.util.List;

public class BookingService {
    private BookingDAO bookingDAO = new BookingDAO();

    public boolean createBooking(Booking booking) {
        return bookingDAO.insert(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }

    public boolean cancelBooking(int id) {
        return bookingDAO.cancel(id);
    }

    public boolean approveBooking(int id) {
        return bookingDAO.approve(id);
    }
}
