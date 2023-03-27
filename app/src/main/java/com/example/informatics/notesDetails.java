package com.example.informatics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class notesDetails extends AppCompatActivity {

    TextView titileofnotedetail, contentofnotedetail;
    FloatingActionButton gotoeditnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);

        titileofnotedetail=findViewById(R.id.titileofnotedetail);
        contentofnotedetail= findViewById(R.id.contentofnoedetail);
        gotoeditnote=findViewById(R.id.gotoeditnote);

//       implementing back button
        Toolbar toolbar = findViewById(R.id.toolbarofnotedetail);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent data = getIntent();


        gotoeditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),editNoteActivity.class);
                intent.putExtra("title",data.getStringExtra("title"));
                intent.putExtra("content",data.getStringExtra("content"));
                intent.putExtra("docId",data.getStringExtra("docId"));
                view.getContext().startActivity(intent);

            }
        });
        titileofnotedetail.setText(data.getStringExtra("title"));
        contentofnotedetail.setText(data.getStringExtra("content"));

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home) {

            onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }
}