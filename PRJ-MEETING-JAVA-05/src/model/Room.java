package model;

import java.math.BigDecimal;

public class Room {
    private int roomId;
    private String roomName;
    private int capacity;
    private String location;
    private String roomType;
    private BigDecimal basePrice;
    private String status; // thêm trạng thái phòng

    // Getter & Setter
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoomType() {
        return roomType;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }
    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}