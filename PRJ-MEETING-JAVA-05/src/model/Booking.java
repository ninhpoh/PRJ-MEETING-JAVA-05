package model;

import java.time.LocalDate;

public class Booking {
    private int bookingId;
    private int userId;
    private int roomId;
    private LocalDate bookingDate;
    private String session;
    private String status;

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }

    public String getSession() { return session; }
    public void setSession(String session) { this.session = session; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}