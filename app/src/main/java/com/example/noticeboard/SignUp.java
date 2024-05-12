package com.example.noticeboard;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class SignUp extends AppCompatActivity {
    //EditText mEmail,mPassword,mSection,mYear,mDepartment,mSemister;
    //Button mRegister;
    //TextView mLogin,madmin;
    DatabaseReference ref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://nwu-app-91f41-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        final EditText mEmail=findViewById(R.id.email);
        final EditText mPassword=findViewById(R.id.password);
       final EditText  mid=findViewById(R.id.stuid);
        final EditText mDepartment=findViewById(R.id.department);
        final EditText mSection=findViewById(R.id.Section);
        final EditText mYear=findViewById(R.id.year);
        final EditText mSemister=findViewById(R.id.semister);
        final Button mRegister=findViewById(R.id.Resister);
        final TextView loginNowBtn=findViewById(R.id.loginNow);

        //final TextView mLogin=findViewById(R.id.AdminResister);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String demail=mEmail.getText().toString().trim();
                final String dpasssword =mPassword.getText().toString().trim();
                final String did=mid.getText().toString().trim();
                final String dsection =mSection.getText().toString().trim();
                final String dyear =mYear.getText().toString().trim();
                final String ddepartment =mDepartment.getText().toString().trim();
                final String dsemister =mSemister.getText().toString().trim();

                if (demail.isEmpty()|| dpasssword.isEmpty()||did.isEmpty()||dsection.isEmpty()||dyear.isEmpty()||ddepartment.isEmpty()||dsemister.isEmpty()){

                    Toast.makeText(SignUp.this, "Fill all the details ", Toast.LENGTH_SHORT).show();

                }
               /* else if(dpasssword.equals(dcompasssword)) {

                    Toast.makeText(SignUp.this, "Password are not matching", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    ref.child("uses").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(dpasssword)){
                                Toast.makeText(SignUp.this, "Password already exit", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                ref.child("users").child(did).child("Email").setValue(demail);
                                ref.child("users").child(did).child("Password").setValue(dpasssword);
                                ref.child("users").child(did).child("Section").setValue(dsection);
                                ref.child("users").child(did).child("Year").setValue(dyear);
                                ref.child("users").child(did).child("Semister").setValue(dsemister);
                                ref.child("users").child(did).child("department").setValue(ddepartment);

                                Toast.makeText(SignUp.this, "User Registered Sucessfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, User.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }

            }
        });
        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }
}