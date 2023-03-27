package com.example.informatics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class notesActivity extends AppCompatActivity {





//    declarating vars
    FloatingActionButton mcreatenotesfab;
    private FirebaseAuth firebaseAuth;

    RecyclerView mrecyclerview;


    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;


    ArrayList<firebasemodel> notes;
//    ArrayList<firebasemodel> notes1;

    firebasemodel notes1;
    notesAdapter  notesadapter;

    FirebaseFirestore db;


    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

//        setting the native android progress bar till notes get loaded.
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(true);
//        progressDialog.setMessage("Fetching notes");
//        progressDialog.show();

//        setting the title of activity
        getSupportActionBar().setTitle("All notes");





        this.mcreatenotesfab=findViewById(R.id.createnotefab);

        mrecyclerview=findViewById(R.id.recyclerView);
        staggeredGridLayoutManager= new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        mrecyclerview.setHasFixedSize(true);



        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        db= FirebaseFirestore.getInstance();
        notes = new ArrayList<firebasemodel>();
        notes1 = new firebasemodel();

        notesadapter= new notesAdapter(notesActivity.this,notes);
        mrecyclerview.setAdapter(notesadapter);
        eventChangeListener();





//        code to crate note with new activity called crateNote activity
        mcreatenotesfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(notesActivity.this,createNote.class));


            }
        });
    }

        public void eventChangeListener(){
            TextView raj= findViewById(R.id.raj);
        db.collection("notes").document(firebaseUser.getUid()).collection("myNotes").orderBy("title", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange dc : value.getDocumentChanges())
                        {
                            if(dc.getType()== DocumentChange.Type.ADDED) {
                                notes1 = dc.getDocument().toObject(firebasemodel.class);
                                notes1.setDocId(dc.getDocument().getId());
                                notes.add(notes1);
                            }



//                            else if (dc.getType()==DocumentChange.Type.REMOVED)
//                            {
//                                notes1= dc.getDocument().toObject(firebasemodel.class);
//                                notes1.setDocId(dc.getDocument().getId());
//                                notes.add(notes1);
//
//                            }
//                            else if(dc.getType()==DocumentChange.Type.MODIFIED)
//                            {
//                                notes1= dc.getDocument().toObject(firebasemodel.class);
//                                notes1.setDocId(dc.getDocument().getId());
//                                notes.add(notes1);
//
//                            }

                            notesadapter.notifyDataSetChanged();
                        }


                    }
                });
        }


//        Query query= firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").orderBy("title",Query.Direction.ASCENDING);


// db.collection("notes").document(firebaseUser.getUid()).collection("myNotes").orderBy("title",Query.Direction.ASCENDING)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (error != null) {
//                            if (progressDialog.isShowing()) {
//
//                                progressDialog.dismiss();
//                            }
//                            Log.e("firestore error",error.getMessage());
//                            Toast.makeText(notesActivity.this, "eror occured", Toast.LENGTH_SHORT).show();
//
//                            return;
//                        }
//                        for(DocumentChange dc : value.getDocumentChanges())
//                        {
//                                if(dc.getType() == DocumentChange.Type.ADDED)
//                            {
//                                notes.add(dc.getDocument().toObject(firebasemodel.class));
//
//
//
//                            }
//
//
//                        }
//                       notesadapter.notifyDataSetChanged();
//                        Toast.makeText(notesActivity.this, "notes get loaded", Toast.LENGTH_SHORT).show();
//
//                        if (progressDialog.isShowing()) {
//
//                            progressDialog.dismiss();
//                        }
//
//
//
//                    }
//
//                });




    public class NoteViewHolder extends  RecyclerView.ViewHolder{
        public  TextView notetitle;
        public  TextView notecontent;
        LinearLayout mnote;
        public NoteViewHolder(@NonNull View itemView)
        {
            super(itemView);
            notetitle=itemView.findViewById(R.id.notetitle);
            notecontent=itemView.findViewById(R.id.notecontent);
            mnote=itemView.findViewById(R.id.note);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(notesActivity.this,MainActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }


}

