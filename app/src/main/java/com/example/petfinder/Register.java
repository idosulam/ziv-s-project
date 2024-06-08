package com.example.petfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference db_ref;

    //TODO need to check that in email we actully got email and not just a string , remember me
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        Button already_registered = findViewById(R.id.already_registered);
        EditText register_email = findViewById(R.id.register_email);
        EditText register_password = findViewById(R.id.register_password);
        EditText register_username = findViewById(R.id.register_username);
        Button register_button = findViewById(R.id.register_button);
        already_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = register_email.getText().toString().trim();
                String password = register_password.getText().toString().trim();
                String username = register_username.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                    Toast.makeText(Register.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                User register_user = new User(email, username, password, "");
                db = FirebaseDatabase.getInstance();
                db_ref = db.getReference("Users");
                db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean found = false;
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            if (childSnapshot.getKey().equals(email)) {
                                found = true;
                                break;
                            }
                        }
                        if (found) {
                            Toast.makeText(Register.this, "User already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            db_ref.child(email).setValue(register_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

}