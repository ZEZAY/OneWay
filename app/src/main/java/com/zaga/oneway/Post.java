package com.zaga.oneway;

public class Post {

    private String msg;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Ranker.class)
    }

    public Post(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
