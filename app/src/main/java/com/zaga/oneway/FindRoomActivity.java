package com.zaga.oneway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindRoomActivity extends AppCompatActivity {

    private static final String TAG = "DocSnippets";

    private ArrayList<Room> rooms;
    private RecyclerView roomsView;
    private RoomsViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_room);

        createRecyclerView();
    }

    private void createRecyclerView() {
        rooms = new ArrayList<>();
        // database here
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Rooms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getData().get("roomName").toString();
                                String detail = document.getData().get("roomDetail").toString();
                                Room room = new Room(name, detail);
                                rooms.add(room);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        buildRecyclerView();
                    }
                });
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
                goToPost.putExtra("RoomName", rooms.get(position).getRoomName());
                goToPost.putExtra("roomDetail", rooms.get(position).getRoomDetail());
                startActivity(goToPost);
            }
        });
    }

}
