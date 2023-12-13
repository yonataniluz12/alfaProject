package com.example.alfaproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class pictureSent extends AppCompatActivity {

    private static final int REQUEST_PICK_IMAGE = 1;

    public StorageReference imagesRef,storageRef;
    ImageView iV;
    Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_sent);

        iV = (ImageView) findViewById(R.id.iV);
        storageRef = FirebaseStorage.getInstance().getReference();
    }






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PICK_IMAGE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Gallery permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void select_item(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PICK_IMAGE);



    }

    public void showImage(View view) {
        if(imagesRef != null){
            imagesRef.getBytes(1920*1080).addOnSuccessListener(bytes -> {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                iV.setImageBitmap(bitmap);
            }).addOnFailureListener(e -> {
                Toast.makeText(pictureSent.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
            })
            ;
        }
        else {Toast.makeText(pictureSent.this,"the image not upload",Toast.LENGTH_LONG).show();}

    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data_back) {
        super.onActivityResult(requestCode, resultCode, data_back);
        if(requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data_back != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] imagedata = byteArrayOutputStream.toByteArray();
                    imagesRef = storageRef.child("image/" + System.currentTimeMillis()+ "jpt");
                    UploadTask uploadTask = imagesRef.putBytes(imagedata);
                    uploadTask.addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(pictureSent.this, "image upload success fully", Toast.LENGTH_LONG).show();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(pictureSent.this, "Failed upload image", Toast.LENGTH_LONG).show();
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(pictureSent.this, "error", Toast.LENGTH_LONG).show();
                }
            }
        }
        else{
            Toast.makeText(pictureSent.this, "select image", Toast.LENGTH_LONG).show();
        }
    }
    public void next2(View view) {
        startActivity(new Intent(pictureSent.this,fdbd.class));
    }
}