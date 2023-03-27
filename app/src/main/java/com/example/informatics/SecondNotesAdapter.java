package com.example.informatics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SecondNotesAdapter extends RecyclerView.Adapter<SecondNotesAdapter.ViewHolder> {
    Context context;
    ArrayList<firebasemodel> notes;

    public SecondNotesAdapter(Context context, ArrayList<firebasemodel> notes) {
        this.context = context;
        this.notes = notes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebasemodel model = notes.get(position);
        holder.title.setText(model.getTitle());
        holder.content.setText(model.getContent());



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,content;
        LinearLayout mnote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.notetitle);
            content=itemView.findViewById(R.id.notecontent);
            mnote=itemView.findViewById(R.id.note);

        }
    }

}
