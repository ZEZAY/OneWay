package com.zaga.oneway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class FindRoomActivity extends AppCompatActivity {

    private ArrayList<Room> rooms;
    private RecyclerView roomsView;
    private RoomsViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_room);

        createRecyclerView();
        buildRecyclerView();
    }

    private void createRecyclerView() {
        rooms = new ArrayList<>();
        rooms.add(new Room("room A", "ha?"));
        rooms.add(new Room("rom-Bj% 555", "ha?"));
        rooms.add(new Room("qwizeet me ya--", "ha?"));
        rooms.add(new Room("room S", "ha?"));
        rooms.add(new Room("what??", "?"));
        // database here
    }

    private void buildRecyclerView() {
        roomsView = findViewById(R.id.rooms_recyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        roomsView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        roomsView.setLayoutManager(layoutManager);
        // specify an adapter
        adapter = new RoomsViewAdapter(rooms);
        roomsView.setAdapter(adapter);

        // set onClick
        adapter.setOnItemClickListener(new RoomsViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent goToPost = new Intent(FindRoomActivity.this, PostRoomActivity.class);
                goToPost.putExtra("Room", rooms.get(position).getRoomName());
                startActivity(goToPost);
            }
        });
    }

}
