package com.zaga.oneway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class PostRoomActivity extends AppCompatActivity {

    private String roomName;
    private String roomDetail;
    private Toolbar roomToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_room);

        roomName = getIntent().getExtras().get("roomName").toString();
        roomToolbar = findViewById(R.id.toolbar);
        roomToolbar.setTitle(roomName);

        roomDetail = getIntent().getExtras().get("roomDetail").toString();
        ((TextView) findViewById(R.id.tv_room_detail)).setText(roomDetail);

        findViewById(R.id.btn_send_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = ((EditText) findViewById(R.id.et_msg)).getText().toString().trim();
                if (!msg.isEmpty()) {
                    // database here
                    Post newPost = new Post(roomName, msg);
                    addPostToDatabase(newPost);

                    Toast.makeText(getApplicationContext(), "send", Toast.LENGTH_LONG).show();
                    ((EditText) findViewById(R.id.et_msg)).setText("");
                }
                else Toast.makeText(getApplicationContext(), "Send Error: text not found", Toast.LENGTH_SHORT);
            }
        });
    }

    private static void addPostToDatabase(Post post){
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        db.collection("Posts").add(post);
    }
}
