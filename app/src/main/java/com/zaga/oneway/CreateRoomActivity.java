package com.zaga.oneway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class CreateRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        findViewById(R.id.btn_create_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((TextView) findViewById(R.id.et_room_name)).getText().toString().trim();
                String detail = ((TextView) findViewById(R.id.et_room_detail)).getText().toString().trim();

                if (name.isEmpty()){
                    Snackbar.make(v, "please Enter room name", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (detail.isEmpty()) detail = "No room's detail";

                Room newRoom = new Room(name, detail);
                Toast.makeText(getApplicationContext(), newRoom.toString(), Toast.LENGTH_LONG).show();
                // database here

                Intent goToBoardIntent = new Intent(CreateRoomActivity.this, BoardActivity.class);
                goToBoardIntent.putExtra("roomName", name);
                startActivity(goToBoardIntent);
            }
        });
    }
}
