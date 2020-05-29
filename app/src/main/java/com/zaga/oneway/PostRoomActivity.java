package com.zaga.oneway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PostRoomActivity extends AppCompatActivity {

    private String roomName;
    private String roomDetail;
    private Toolbar roomToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_room);

        roomName = getIntent().getExtras().get("Room").toString();
        roomToolbar = findViewById(R.id.toolbar);
        roomToolbar.setTitle(roomName);

//        database reader here !!!
//        roomDetail = ...
        ((TextView) findViewById(R.id.tv_room_detail)).setText(roomDetail);

        findViewById(R.id.btn_send_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = ((EditText) findViewById(R.id.et_msg)).getText().toString().trim();
                if (!msg.isEmpty()) {
                    // database here
                }
            }
        });
    }
}
