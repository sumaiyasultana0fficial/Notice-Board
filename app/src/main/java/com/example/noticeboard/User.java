package com.example.noticeboard;

import android.annotation.SuppressLint;
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

public class User extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CardView userEbook = findViewById(R.id.seeEbook);
        CardView userImage = findViewById(R.id.seeNotice);
        CardView userGallery = findViewById(R.id.seeImage);
        Button userLogOut = findViewById(R.id.userLogOut);

        userEbook.setOnClickListener(this);
        userImage.setOnClickListener(this);
        userGallery.setOnClickListener(this);
        userLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.seeEbook) {
            Intent ebookIntent = new Intent(this, seeEbook.class);
            startActivity(ebookIntent);
        } else if (v.getId() == R.id.seeNotice) {
            Intent noticeIntent = new Intent(this, UserNoticeView.class);
            startActivity(noticeIntent);
        } else if (v.getId() == R.id.seeImage) {
            Intent galleryIntent = new Intent(this, GalleryActivity.class);
            startActivity(galleryIntent);
        }
        else if(v.getId() == R.id.userLogOut){
            Intent userLogout = new Intent(this, LogIn.class);
            startActivity(userLogout);
        }
    }
}
