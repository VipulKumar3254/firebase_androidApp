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

public class editNoteActivity extends AppCompatActivity {

    EditText edittitleofnote;
    EditText editcontentofnote;
    FloatingActionButton saveeditnote;
    ProgressBar progressBarofeditnoteactivity;
    
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Intent data = getIntent();


        edittitleofnote=findViewById(R.id.edittitleofnote);
        editcontentofnote = findViewById(R.id.editcontentofnote);
        saveeditnote=findViewById(R.id.saveeditnote);
        progressBarofeditnoteactivity=findViewById(R.id.progessbarofeditnoteactivity);
        
        
        //initializing firestore variables
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        

        Toolbar toolbar =findViewById(R.id.toolbarofeditnote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String notetitle=data.getStringExtra("title");
        String notecontent =data.getStringExtra("content");
        edittitleofnote.setText(notetitle);
        editcontentofnote.setText(notecontent);


//        saving the edited note on firebase.
        saveeditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle =edittitleofnote.getText().toString();
                String newContent =editcontentofnote.getText().toString();
                if (newTitle.isEmpty() ||newContent.isEmpty())
                {
                    Toast.makeText(editNoteActivity.this, "title and content can't be empty", Toast.LENGTH_SHORT).show();
//                     return;  // we have to test the feature of return here
                }
                else{
                    DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(data.getStringExtra("docId"));
                    Map<String,Object> note=new HashMap<>();
                    note.put("title",newTitle);
                    note.put("content",newContent);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressBarofeditnoteactivity.setVisibility(View.VISIBLE);
                            startActivity(new Intent(editNoteActivity.this,notesActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(editNoteActivity.this, "failed to update note", Toast.LENGTH_SHORT).show();
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