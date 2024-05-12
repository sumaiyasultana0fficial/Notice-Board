package com.example.noticeboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CardView uploadNotice = findViewById(R.id.addNotice);
        CardView addGalleryImage = findViewById(R.id.addGalleryImage);
        CardView addEbook = findViewById(R.id.addEbook);
        Button adminLogout = findViewById(R.id.adminLogOut);

        uploadNotice.setOnClickListener(this);
        addGalleryImage.setOnClickListener(this);
        addEbook.setOnClickListener(this);
        adminLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.addNotice) {
            Intent intent = new Intent(MainActivity.this, UploadNotice.class);
            startActivity(intent);

        } else if (v.getId() == R.id.addGalleryImage) {
            Intent intent = new Intent(MainActivity.this, UploadImage.class);
            startActivity(intent);
        } else if (v.getId() == R.id.addEbook) {
            Intent intent = new Intent(MainActivity.this, UploadPdfActivity.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.adminLogOut){
            Intent adminLogout = new Intent(this, LogIn.class);
            startActivity(adminLogout);
        }

    }
}