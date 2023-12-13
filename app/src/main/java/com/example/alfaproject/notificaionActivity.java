package com.example.alfaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class notificaionActivity extends AppCompatActivity {
    Intent gi;
    EditText eTNOti;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaion);
        eTNOti = findViewById(R.id.editTextText);
        gi = getIntent();
        boolean clicked = gi.getBooleanExtra("notification_clicked", false);
        if (clicked) {
            Toast.makeText(this, "Notification clicked!", Toast.LENGTH_LONG).show();
        }
    }

    public void textNoti(View view) {
        String text = eTNOti.getText().toString();
        NotificationHelper.showNotification(this, text);
    }

    
    public void TextNotiTime(View view) {
        String text = eTNOti.getText().toString();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        NotificationHelper.showNotificationBtnTime(this,text);
        
    }
}