package com.example.informatics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createNote extends AppCompatActivity {


    ProgressBar  mprogressbarofcreatenote;
    EditText mcreateTitleNote ,mcreatecontentNote;
    FloatingActionButton mSaveNote;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);


        mSaveNote=findViewById(R.id.savenote);
        mcreatecontentNote=findViewById(R.id.createcontentofnote);
        mcreateTitleNote=findViewById(R.id.createtitleofnote);
        Toolbar toolbar = findViewById(R.id.toolbarofcreatenote);
        mprogressbarofcreatenote= findViewById(R.id.progessbarofcreatenoteactivity);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        mSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=mcreateTitleNote.getText().toString();
                String content=mcreatecontentNote.getText().toString();
                if(title.isEmpty()|| content.isEmpty())
                {
                    Toast.makeText(createNote.this, "both fields are required", Toast.LENGTH_SHORT).show();

                }
                else{
                    DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document();
                    Map<String,Object> note=new  HashMap<>();
                    note.put("title",title);
                    note.put("content",content);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            mprogressbarofcreatenote.setVisibility(View.VISIBLE);

                            startActivity(new Intent(createNote.this,notesActivity .class));


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(createNote.this, "failed to create note", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(createNote.this,notesActivity.class));



                        }
                    });

                }
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home) {

            onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }
}