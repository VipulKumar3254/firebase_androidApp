package com.example.informatics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {
    private EditText msignUpEmail, mSignUpPassword;
    private RelativeLayout msignup;
    private TextView gotoLogin;

    private FirebaseAuth firebaseAuth; //first part

    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(signup.this, "Verification email is sent, verify and login in ", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(signup.this,MainActivity.class));



                }
            });

        }
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.msignUpEmail = findViewById(R.id.signupEmail);
        this.mSignUpPassword = findViewById(R.id.signupPassword);
        this.msignup = findViewById(R.id.signup);
        this.gotoLogin= findViewById(R.id.gotologin);

        firebaseAuth=FirebaseAuth.getInstance(); //second part


        this.gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this,MainActivity.class);
                startActivity(intent);

            }
        });
        
        
        

        this.msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail= msignUpEmail.getText().toString().trim();
                String password = mSignUpPassword.getText().toString().trim();
                if(mail.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT);

                }
                else if(password.length()<7){

                    Toast.makeText(getApplicationContext(),"password must be greater than 7 digits",Toast.LENGTH_SHORT);


                }
                else {
//                    need to work on it .
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(signup.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();

                            }
                            else{
                                Toast.makeText(signup.this, "error in saving the user .", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
            }
        });  /*done*/


    }

//    

}