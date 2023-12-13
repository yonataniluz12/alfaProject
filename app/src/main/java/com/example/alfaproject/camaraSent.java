package com.example.alfaproject;



import androidx.annotation.NonNull;
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
import java.io.FileNotFoundException;
import java.io.IOException;

public class camaraSent extends AppCompatActivity {
    public static StorageReference storageRef,imagesRef;

    private static final int REQUEST_CAMERA_PERMISSION = 50;


    ImageView iV;
    Uri imageUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_sent);

        iV = findViewById(R.id.imageView);
        storageRef = FirebaseStorage.getInstance().getReference();
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data_back) {
        super.onActivityResult(requestCode, resultCode, data_back);
        if (requestCode == REQUEST_CAMERA_PERMISSION && resultCode == Activity.RESULT_OK) {
            if(data_back != null)
            {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] imagedata = byteArrayOutputStream.toByteArray();
                    imagesRef = storageRef.child("image/" +System.currentTimeMillis()+ "jpt");
                    UploadTask uploadTask = imagesRef.putBytes(imagedata);
                    uploadTask.addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(camaraSent.this, "image upload success fully", Toast.LENGTH_LONG).show();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(camaraSent.this, "Failed upload image", Toast.LENGTH_LONG).show();
                    });
                }
                catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(camaraSent.this, "error", Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            Toast.makeText(camaraSent.this, "select image", Toast.LENGTH_LONG).show();
            }
        }





    public void goCamara(View view) {
        Intent takePicIntent = new Intent();
        takePicIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicIntent, REQUEST_CAMERA_PERMISSION);
        }
    }


    public void next4(View view) {
        startActivity(new Intent(camaraSent.this,notificaionActivity.class));
    }

    public void Show_image(View view) {
        if(imagesRef != null){
            imagesRef.getBytes(1920*1080).addOnSuccessListener(bytes -> {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                iV.setImageBitmap(bitmap);
            }).addOnFailureListener(e -> {
                Toast.makeText(camaraSent.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
            })
            ;
        }
        else {Toast.makeText(camaraSent.this,"the image not upload",Toast.LENGTH_LONG).show();}

    }
}