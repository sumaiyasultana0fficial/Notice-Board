package com.example.noticeboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class AdminLogIn extends AppCompatActivity {
    DatabaseReference ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://nwu-app-91f41-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        final EditText adpass=findViewById(R.id.apassword);
        final  EditText Aid=findViewById(R.id.aid);
        final Button  adLoginBtn=findViewById(R.id.adlogin);

        adLoginBtn.setOnClickListener(v -> {
            final String passTxt=adpass.getText().toString();
            final String idTxt=Aid.getText().toString();
            if(passTxt.isEmpty()||idTxt.isEmpty())
            {
                Toast.makeText(AdminLogIn.this, "Please enter your email or password", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ref.child("admin").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(idTxt)) {
                            final String getpassword=dataSnapshot.child(idTxt).child("Password").getValue(String.class);
                            assert getpassword != null;
                            if(getpassword.equals(passTxt)){
                                Toast.makeText(AdminLogIn.this,"Successfully Logged In",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminLogIn.this, MainActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(AdminLogIn.this,"Wrong Password",Toast.LENGTH_SHORT).show();

                            }

                        }
                        else {
                            Toast.makeText(AdminLogIn.this,"Wrong ID",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });



    }
}