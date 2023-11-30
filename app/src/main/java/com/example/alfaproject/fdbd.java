package com.example.alfaproject;

import static com.example.alfaproject.FBref.refText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class fdbd extends AppCompatActivity {
    EditText eT;
    TextView tV;
    String id,yourText;
    String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdbd);
        eT = findViewById(R.id.eT);
        tV = findViewById(R.id.tv);
        id = "text";
        yourText = "";
        refText.child(id).setValue(yourText);
        fdbdClass fdbdclass = new fdbdClass(id,yourText);
        ValueEventListener stuListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                str = "";
                for (DataSnapshot data : snapshot.getChildren()){
                    String value = data.getValue(String.class);
                    str = value;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        refText.addValueEventListener(stuListener);
    }

    public void sentText(View view) {
        refText.child(id).setValue(eT.getText().toString());

    }

    public void getText(View view) {
        tV.setText(str);
    }

    public void next3(View view) {
        startActivity(new Intent(fdbd.this,camaraSent.class)) ;
    }
}