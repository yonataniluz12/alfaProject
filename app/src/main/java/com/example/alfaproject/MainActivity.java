package com.example.alfaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
            {
                startActivity(new Intent(MainActivity.this,Welcome.class));
            }
    }



    public void logIn(View view) {
        EditText emailET = findViewById(R.id.editTextTextEmailAddress);
        EditText passwordET = findViewById(R.id.editTextTextPassword);
        mAuth.signInWithEmailAndPassword(emailET.getText().toString(),passwordET.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(MainActivity.this,Welcome.class));
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this,"login faild ",Toast.LENGTH_SHORT).show();}
                    }
                });

    }

    public void Register(View view) {
        EditText emailET = findViewById(R.id.editTextTextEmailAddress);
        EditText passwordET = findViewById(R.id.editTextTextPassword);
        mAuth.createUserWithEmailAndPassword(emailET.getText().toString(),passwordET.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener <AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(MainActivity.this,Welcome.class));
                        }
                        else {
                            Toast.makeText(MainActivity.this,"register faild ",Toast.LENGTH_SHORT).show();}
                    }
                });
    }

    public void next(View view) {
        startActivity(new Intent(MainActivity.this,pictureSent.class));
    }
}