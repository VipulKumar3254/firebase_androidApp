package com.example.informatics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class editNoteActivity extends AppCompatActivity {

    EditText edittitleofnote;
    EditText editcontentofnote;
    FloatingActionButton saveeditnote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Intent data = getIntent();


        edittitleofnote=findViewById(R.id.edittitleofnote);
        editcontentofnote = findViewById(R.id.editcontentofnote);
        saveeditnote=findViewById(R.id.saveeditnote);

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