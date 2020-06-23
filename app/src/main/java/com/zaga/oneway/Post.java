package com.zaga.oneway;

public class Post {

    private String room;
    private String msg;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Ranker.class)
    }

    public Post(String room, String msg) {
        this.room = room;
        this.msg = msg;
    }

    public String getRoom() {
        return room;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
