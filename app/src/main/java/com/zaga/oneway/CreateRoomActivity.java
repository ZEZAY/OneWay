package com.zaga.oneway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateRoomActivity extends AppCompatActivity {

    private static final String TAG = "DocSnippets";

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
                addRoomDatabase(newRoom);

                Intent goToBoardIntent = new Intent(CreateRoomActivity.this, BoardActivity.class);
                goToBoardIntent.putExtra("roomName", name);
                startActivity(goToBoardIntent);
            }
        });
    }

    private static void addRoomDatabase(Room room){
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        db.collection("Rooms")
                .add(room)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
