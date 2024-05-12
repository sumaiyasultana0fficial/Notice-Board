package com.example.noticeboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {
    DatabaseReference ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://nwu-app-91f41-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       final EditText fpass=findViewById(R.id.lpassword);
        final EditText fid=findViewById(R.id.lid);
        final Button loginBtn=findViewById(R.id.LoginBtn);
        final TextView registerBtn=findViewById(R.id.RegisterNow);
        final TextView adlogBtn=findViewById(R.id.adLogin);

        loginBtn.setOnClickListener(v -> {
            final String passTxt=fpass.getText().toString();
            final String idTxt=fid.getText().toString();
            if(passTxt.isEmpty()||idTxt.isEmpty())
            {
                Toast.makeText(LogIn.this, "Please enter your email or password", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(idTxt)) {
                            final String getpassword=dataSnapshot.child(idTxt).child("Password").getValue(String.class);
                            assert getpassword != null;
                            if(getpassword.equals(passTxt)){
                                Toast.makeText(LogIn.this,"Successfully Loged In",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LogIn.this, User.class));
                                finish();
                            }
                            else {
                                Toast.makeText(LogIn.this,"Wrong Password",Toast.LENGTH_SHORT).show();

                            }

                        }
                        else {
                            Toast.makeText(LogIn.this,"Wrong ID",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });
        registerBtn.setOnClickListener(v -> startActivity(new Intent(LogIn.this, SignUp.class)));
        adlogBtn.setOnClickListener(v -> startActivity(new Intent(LogIn.this, AdminLogIn.class)));
    }
}