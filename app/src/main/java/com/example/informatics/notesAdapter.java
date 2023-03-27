package com.example.informatics;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class notesAdapter extends RecyclerView.Adapter<notesAdapter.NotesViewHolder>{

    FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Context context;
    ArrayList<firebasemodel> notes;  // creating a array of type firebasemodel to storing data of notes.

    public notesAdapter(Context context,ArrayList<firebasemodel> notes) {
        this.context= context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public notesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_layout,parent,false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notesAdapter.NotesViewHolder holder, int position) {
        int colorcode=getRandomColor();
        firebasemodel model = notes.get(position);
        holder.mnote.setBackgroundColor(holder.itemView.getResources().getColor(colorcode,null));
        holder.title.setText(model.getTitle());
        holder.content.setText(model.getContent());

//
//
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                we have to open a new noteDetail activity
                Intent intent = new Intent(view.getContext(),notesDetails.class);
                intent.putExtra("title",model.getTitle());
                intent.putExtra("content",model.getContent());
                intent.putExtra("docId",model.getDocId());
                view.getContext().startActivity(intent);
            }
        });

        ImageView popupbutton = holder.itemView.findViewById(R.id.menupopbutton);
        popupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
                popupMenu.setGravity(Gravity.END);
                popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                        Intent intent = new Intent(view.getContext(),editNoteActivity.class);
                        intent.putExtra("title",model.getTitle());
                        intent.putExtra("content",model.getContent());
                        intent.putExtra("docId",model.getDocId());
                        view.getContext().startActivity(intent);

                        return false;
                    }
                });

                popupMenu.getMenu().add("delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                        firebaseFirestore=FirebaseFirestore.getInstance();
                        firebaseAuth=FirebaseAuth.getInstance();
                        firebaseUser=firebaseAuth.getCurrentUser();

                        DocumentReference df = firebaseFirestore.collection("notes").document(firebaseAuth.getInstance().getCurrentUser().getUid()).collection("myNotes").document(model.getDocId());
                        df.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "note deleted", Toast.LENGTH_SHORT).show();
//                                view.getContext().startActivity(new Intent(view.getContext(),MainActivity.class));



                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("can't delete the note because of ",e.getMessage());
                                Toast.makeText(context, "failed to delete the note.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return true;
                    }
                });

                popupMenu.show();
            }
        });

    }



    private int getRandomColor() {

            List<Integer> colorcode = new ArrayList<>();
            colorcode.add(R.color.color1);
            colorcode.add(R.color.color2);
            colorcode.add(R.color.color3);
            colorcode.add(R.color.color4);
            colorcode.add(R.color.color5);
            colorcode.add(R.color.color6);
            colorcode.add(R.color.color7);
            colorcode.add(R.color.color9);
            colorcode.add(R.color.color10);

            Random random = new Random();
            int number = random.nextInt(colorcode.size());

            return colorcode.get(number);



    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public class NotesViewHolder extends  RecyclerView.ViewHolder{
        TextView title ,content;
        LinearLayout mnote;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notetitle);
            content=itemView.findViewById(R.id.notecontent);
            mnote = itemView.findViewById(R.id.note);


        }
    }
}