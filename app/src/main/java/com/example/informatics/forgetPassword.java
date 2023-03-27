package com.example.informatics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class forgetPassword extends AppCompatActivity {

    private EditText mforgetpassword;
    private Button mpasswordRecoverButton;
    private TextView mgoBackToLogin;

        private FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        this.mforgetpassword= findViewById((R.id.forgetPassword));
        this.mpasswordRecoverButton= findViewById((R.id.passwordrecoverbutton));
        this.mgoBackToLogin= findViewById((R.id.gobacktologin));

        firebaseAuth = firebaseAuth.getInstance();

        mgoBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgetPassword.this,MainActivity.class);


            }
        });

        mpasswordRecoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mforgetpassword.getText().toString().trim();
                if(mail.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter mail first",Toast.LENGTH_SHORT).show();
                }
                else{

                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override

                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(forgetPassword.this, "mail send recover password by mail", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgetPassword.this,MainActivity.class));
                            }
                            else{
                                Toast.makeText(forgetPassword.this, "Please double check the email", Toast.LENGTH_SHORT).show();
                                
                            }

                        }
                    });
                }
            }
        });

    }
}