package com.example.alfaproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBref {
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();

    public static DatabaseReference refText = FBDB.getReference("text");

}
