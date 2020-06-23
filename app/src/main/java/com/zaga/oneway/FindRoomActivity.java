package com.zaga.oneway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindRoomActivity extends AppCompatActivity {

    private static final String TAG = "DocSnippets";

    private Toolbar roomToolbar;
    ArrayList<Room> rooms;
    private RecyclerView roomsView;
    private RoomsViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_room);
        // add bar
        roomToolbar = findViewById(R.id.toolbar);
        roomToolbar.setTitle("");
        setSupportActionBar(roomToolbar);

        createRecyclerView();
    }

    // add bar's items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.findroom_bar_menu, menu);
        return true;
    }

    // add bar's items action
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.renew_itemBar) {
            Toast.makeText(getApplicationContext(), "renew", Toast.LENGTH_SHORT).show();
            createRecyclerView();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                goToPost.putExtra("roomName", rooms.get(position).getRoomName());
                goToPost.putExtra("roomDetail", rooms.get(position).getRoomDetail());
                startActivity(goToPost);
            }
        });
    }

}
