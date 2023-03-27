package com.example.informatics;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ProgressBar mprogressbarofmainactivity;
    private EditText memail,mpassword;
    private RelativeLayout mlogin,msignup ;
    private TextView forgetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        this.memail= findViewById(R.id.loginEmail);
        this.mpassword= findViewById(R.id.loginPassword);
        this.mlogin= findViewById(R.id.login);
        this.msignup= findViewById(R.id.signup);
        this.forgetPassword= findViewById(R.id.gotoForgetPassword);
        this.mprogressbarofmainactivity= findViewById(R.id.mainactivityprogessbar);



        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,notesActivity.class));


        }
//
        this.msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(MainActivity.this, com.example.informatics.signup.class));

            }
        });
//
        this.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, com.example.informatics.forgetPassword.class));

            }
        });
//
        this.mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= memail.getText().toString().trim();
                String password  = mpassword.getText().toString().trim();
                if(email.isEmpty()|| password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"fill the details first",Toast.LENGTH_SHORT).show();

                } else {

//                    login the user
                    mprogressbarofmainactivity.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                checkMailVerification();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                                mprogressbarofmainactivity.setVisibility(View.INVISIBLE);

                            }

                        }
                    });
                }
            }
        });

    }

    private void checkMailVerification(){
        FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified())
        {
            Toast.makeText(this, "logged in", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this,notesActivity.class));
        }
        else{
            Toast.makeText(this, "verify mail first", Toast.LENGTH_SHORT).show();
            mprogressbarofmainactivity.setVisibility(View.INVISIBLE);

        }

    }
}