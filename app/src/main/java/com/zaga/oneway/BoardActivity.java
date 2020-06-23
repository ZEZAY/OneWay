package com.zaga.oneway;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private static final String TAG = "DocSnippets";

    private Toolbar roomToolbar;
    private String roomName;
    private ArrayList<Post> posts;
    private RecyclerView postsView;
    private PostsViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        // add bar
        roomName = getIntent().getExtras().get("roomName").toString();
        roomToolbar = findViewById(R.id.toolbar);
        roomToolbar.setTitle(roomName);
        setSupportActionBar(roomToolbar);

        createRecyclerView();
    }

    // add bar's items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.board_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.removeAll_itemBar) {
            Toast.makeText(getApplicationContext(), "removed all", Toast.LENGTH_SHORT).show();
            adapter.updateData(new ArrayList<Post>());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createRecyclerView() {
        posts = new ArrayList<>();
        // database here
        db = FirebaseFirestore.getInstance();
        db.collection("Posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String room = document.getData().get("room").toString();
                                String msg = document.getData().get("msg").toString();
                                if (roomName.equals(room))
                                    posts.add(new Post(room, msg));
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        buildRecyclerView();
                    }
                });
    }

    private void buildRecyclerView() {
        postsView = findViewById(R.id.board_recyclerView);
        postsView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        postsView.setLayoutManager(layoutManager);
        adapter = new PostsViewAdapter(posts);
        postsView.setAdapter(adapter);

        // set onClick
        adapter.setOnItemClickListener(new PostsViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
               // some action here?
                Toast.makeText(getApplicationContext(), "removed", Toast.LENGTH_SHORT).show();
                adapter.removeItem(position);
            }
        });
    }

}
