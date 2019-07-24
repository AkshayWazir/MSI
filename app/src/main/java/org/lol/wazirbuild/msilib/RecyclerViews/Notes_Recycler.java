package org.lol.wazirbuild.msilib.RecyclerViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.lol.wazirbuild.msilib.R;
import org.lol.wazirbuild.msilib.notes_category_Activity;

public class Notes_Recycler extends RecyclerView.Adapter<Notes_Recycler.viewHolder> {
    Context context;

    public Notes_Recycler(Context context){
        this.context=context;
    }

    @NonNull
    @Override
    public Notes_Recycler.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_reycler_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Notes_Recycler.viewHolder holder, int position) {
        holder.Title.setText("Maths");

        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,notes_category_Activity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView Title;
        ConstraintLayout c;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.Notes_Title);
            c=itemView.findViewById(R.id.Notes_recycler_row);
        }
    }


}
