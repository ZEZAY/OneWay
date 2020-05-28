package com.zaga.oneway;

public class Room {

    private String roomName;
    private String roomDetail;

    public Room() {
        // Default constructor required for calls to DataSnapshot.getValue(Ranker.class)
    }

    public Room(String roomName, String roomDetail) {
        this.roomName = roomName;
        this.roomDetail = roomDetail;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDetail() {
        return roomDetail;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", roomName, roomDetail);
    }
}
