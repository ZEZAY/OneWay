package com.zaga.oneway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {

    private Toolbar roomToolbar;
    private ArrayList<Post> posts;
    private RecyclerView postsView;
    private PostsViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        // add bar
        roomToolbar = findViewById(R.id.toolbar);
        roomToolbar.setTitle(""); // remove app name
        setSupportActionBar(roomToolbar);

        createRecyclerView();
        buildRecyclerView();
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
        posts.add(new Post("555"));
        posts.add(new Post("fyck"));
        posts.add(new Post("ha??"));
        posts.add(new Post("what the ---"));
        posts.add(new Post("get some food"));
        // database here
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
