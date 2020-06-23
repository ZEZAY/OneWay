package com.zaga.oneway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CreateRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        findViewById(R.id.btn_create_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = ((TextView) findViewById(R.id.et_room_name)).getText().toString().trim();
                String detail = ((TextView) findViewById(R.id.et_room_detail)).getText().toString().trim();

                if (name.isEmpty()){
                    Snackbar.make(v, "please Enter room name", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (detail.isEmpty()) detail = "No room's detail";
                final Room newRoom = new Room(name, detail);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Rooms").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String thisName = document.getData().get("roomName").toString();
                                        if (name.equals(thisName)) {
                                            Snackbar.make(getCurrentFocus(), "this name already exists", Snackbar.LENGTH_SHORT).show();
                                            ((TextView) findViewById(R.id.et_room_name)).setText("");
                                            return;
                                        }
                                    }
                                    Toast.makeText(getApplicationContext(), newRoom.toString(), Toast.LENGTH_LONG).show();
                                    // database here
                                    addRoomToDatabase(newRoom);

                                    Intent goToBoardIntent = new Intent(CreateRoomActivity.this, BoardActivity.class);
                                    goToBoardIntent.putExtra("roomName", name);
                                    startActivity(goToBoardIntent);
                                }
                            }
                        });
                }
        });
    }

    private static void addRoomToDatabase(Room room){
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        db.collection("Rooms").add(room);
    }

}
